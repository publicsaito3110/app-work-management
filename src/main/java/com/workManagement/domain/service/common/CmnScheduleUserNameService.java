package com.workManagement.domain.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.Const;
import com.workManagement.common.logic.CmnScheduleLogic;
import com.workManagement.common.logic.CommonLogic;
import com.workManagement.domain.model.bean.CmnScheduleUserNameBean;
import com.workManagement.domain.model.dto.ScheduleUserNameDto;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;
import com.workManagement.domain.repository.ScheduleTimeRepository;
import com.workManagement.domain.repository.ScheduleUserNameRepository;
import com.workManagement.domain.service.BaseService;

/**
 * @author saito
 *
 */
@Service
@Transactional
public class CmnScheduleUserNameService extends BaseService {

	@Autowired
	private ScheduleUserNameRepository scheduleUserNameRepository;

	@Autowired
	private ScheduleTimeRepository scheduleTimeRepository;


	/**
	 * [共通 Service] 確定スケジュール登録済み全ユーザ処理
	 *
	 * @param year 年
	 * @param month 月
	 * @param lastDateYmd 年月の最終日の日付
	 * @return CmnScheduleUserNameBean
	 */
	public CmnScheduleUserNameBean generateScheduleUserName(int year, int month, String lastDateYmd) {

		// 1ヵ月分の確定スケジュールをユーザ毎に登録済みのユーザ名で取得
		List<ScheduleUserNameDto> scheduleUserNameList = selectScheduleUserName(year, month);
		// スケジュール時間区分を取得
		ScheduleTimeEntity scheduleTimeEntity = selectScheduleTime(lastDateYmd);
		// 取得した確定スケジュールを2次元配列に変換
		String[][] scheduleUserNameArray = calcScheduleUserNameArray(scheduleUserNameList, scheduleTimeEntity);

		// Beanにセット
		CmnScheduleUserNameBean cmnScheduleUserNameBean = new CmnScheduleUserNameBean(scheduleTimeEntity, scheduleUserNameArray);
		return cmnScheduleUserNameBean;
	}


	/**
	 * 確定スケジュール登録済みユーザ配列処理
	 *
	 * <p>
	 * 確定スケジュールとスケジュール時間区分から該当する日付と時間区分に登録されているユーザのユーザ名を配列で取得する<br>
	 * ただし、スケジュール時間区分の登録可能数だけ判別される<br>
	 * 日付とスケジュール時間区分に登録しているユーザが存在するとき、ユーザー名を格納する(ユーザ毎に改行タグ&lt;br&gt;が追加される)
	 *
	 * @param scheduleUserNameList ユーザーごとの確定スケジュール
	 * @param scheduleTimeEntity スケジュール時間区分
	 * @return String[][] 該当する日付と時間区分に登録されているユーザ名を格納した配列<br>
	 * (要素: [日付][スケジュール時間区分])
	 */
	private String[][] calcScheduleUserNameArray(List<ScheduleUserNameDto> scheduleUserNameList, ScheduleTimeEntity scheduleTimeEntity) {

		// スケジュールに登録されているユーザを格納するための変数 (要素: [日付][スケジュール時間区分])
		String[][] scheduleUserNameArray = new String[31][Const.SCHEDULE_RECORDABLE_MAX_DIVISION];

		// 共通Logicクラス
		CommonLogic commonLogic = new CommonLogic();

		// スケジュールの判定を行うための共通Logicクラス
		CmnScheduleLogic cmnScheduleLogic = new CmnScheduleLogic();

		// 確定スケジュールに登録されているユーザだけループ
		for (ScheduleUserNameDto scheduleUserNameDto: scheduleUserNameList) {

			// 登録されているユーザ名を取得
			String userName = scheduleUserNameDto.getUserName();

			// ユーザの1ヵ月分のスケジュールを日付ごとに取得
			List<String> scheduleDayList = scheduleUserNameDto.scheduleFormatScheduleDayList();

			// スケジュールの日付だけループ
			for (int i = 0; i < scheduleDayList.size(); i++) {

				// 日付ごとのスケジュールを取得し、スケジュールが登録されているかどうかを判定した配列を取得
				String scheduleDay = scheduleDayList.get(i);
				Boolean[] isScheduleRecordedArray = cmnScheduleLogic.toIsScheduleArray(scheduleDay, scheduleTimeEntity);

				//isScheduleRecordedArray(スケジュール時間の区分)だけループする
				for (int j = 0; j < isScheduleRecordedArray.length; j++) {

					if (isScheduleRecordedArray[j] == null) {
						// スケジュール時間区分がないとき、ループに戻る
						continue;
					} else if (isScheduleRecordedArray[j]) {
						// スケジュールが登録されているとき

						// スケジュールにユーザ名と改行タグを追加する
						String userSchedule = commonLogic.changeEmptyByNull(scheduleUserNameArray[i][j]) +  userName + Const.HTML_TAG_BR;
						scheduleUserNameArray[i][j] = userSchedule;
					}
				}
			}
		}
		return scheduleUserNameArray;
	}


	/**
	 * [Repository] 確定スケジュール登録済みユーザ検索処理
	 *
	 * <p>
	 * 年月とユーザIDと一致する確定スケジュールに登録済みのユーザをユーザ名で全て取得する<br>
	 * ただし、登録済みのユーザが1人もないときはEmptyとなる
	 *
	 * @param year 取得したいスケジュールの年
	 * @param month 取得したいスケジュールの月
	 * @return List<ScheduleUserNameDto> ユーザ毎の確定スケジュール
	 */
	private List<ScheduleUserNameDto> selectScheduleUserName(int year, int month) {

		// 年月(YYYYMM)に変換
		String ym = new CommonLogic().changeYm(year, month);
		return scheduleUserNameRepository.selectScheduleUserName(ym);
	}


	/**
	 * [Repository] スケジュール時間区分検索処理
	 *
	 * <p>
	 * 取得したい日付(ymd)から該当するスケジュール時間区分を取得する<br>
	 * また、現在日(ymd)に該当するスケジュール時間区分が複数登録されているときは最新のスケジュール時間区分が取得される<br>
	 * ただし、スケジュール時間区分が何も登録されていないときはnullとなる
	 *
	 * @param ymd 取得したいスケジュール時間区分の日付
	 * @return ScheduleTimeEntity スケジュール時間区分
	 */
	private ScheduleTimeEntity selectScheduleTime(String ymd) {

		ScheduleTimeEntity scheduleTimeEntity = scheduleTimeRepository.selectScheduleTime(ymd);
		return scheduleTimeEntity;
	}
}
