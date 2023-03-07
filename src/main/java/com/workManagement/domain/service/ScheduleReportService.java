package com.workManagement.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.Const;
import com.workManagement.common.logic.CmnScheduleLogic;
import com.workManagement.common.logic.CmnScheduleTimeLogic;
import com.workManagement.common.logic.CommonLogic;
import com.workManagement.domain.model.bean.ScheduleReportBean;
import com.workManagement.domain.model.dto.ScheduleCountMonthDto;
import com.workManagement.domain.model.dto.ScheduleCountYearDto;
import com.workManagement.domain.model.dto.ScheduleUserNameDto;
import com.workManagement.domain.model.dto.ScheduleYearDto;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;
import com.workManagement.domain.repository.ScheduleCountMonthRepository;
import com.workManagement.domain.repository.ScheduleCountYearRepository;
import com.workManagement.domain.repository.ScheduleTimeRepository;
import com.workManagement.domain.repository.ScheduleUserNameRepository;
import com.workManagement.domain.repository.ScheduleYearRepository;

/**
 * @author saito
 *
 */
@Service
@Transactional
public class ScheduleReportService extends BaseService {

	@Autowired
	private ScheduleTimeRepository scheduleTimeRepository;

	@Autowired
	private ScheduleCountMonthRepository scheduleCountMonthRepository;

	@Autowired
	private ScheduleCountYearRepository scheduleCountYearRepository;

	@Autowired
	private ScheduleYearRepository scheduleYearRepository;

	@Autowired
	private ScheduleUserNameRepository scheduleUserNameRepository;


	/**
	 * 確定スケジュール勤務状況取得機能<br>
	 * [Service] (/schedule-decision/report)
	 *
	 * @param ym RequestParameter 取得する確定スケジュールの年月
	 * @return ScheduleDecisionReportBean
	 */
	public ScheduleReportBean scheduleReport(String ym) {

		// 共通ロジッククラス
		CommonLogic commonLogic = new CommonLogic();
		// 年月が指定されていないとき、現在の年月を取得
		String trimYm = ym;
		if (trimYm == null) {
			LocalDate nowLd = LocalDate.now();
			trimYm = commonLogic.changeYm(nowLd.getYear(), nowLd.getMonthValue());
		}
		// 日付をそれぞれ配列で取得
		String[] yearMonthDayArray = commonLogic.changeYmdArray(trimYm, "01");
		// 月次の勤務日数を取得
		List<ScheduleCountMonthDto> scheduleCountMonthList = selectScheduleCountMonth(trimYm, yearMonthDayArray[0]);
		// 年次の勤務日数を取得
		List<ScheduleCountYearDto> scheduleWorkCountYearList = selectScheduleCountYear(yearMonthDayArray[0]);
		// ユーザごとの1年分のスケジュールを取得
		List<ScheduleYearDto> scheduleYearList = selectScheduleYear(yearMonthDayArray[0]);
		// ユーザごとの1ヵ月分のスケジュールを取得
		List<ScheduleUserNameDto> scheduleMonthList = selectScheduleUserName(trimYm, yearMonthDayArray[0]);
		// 1年分のスケジュール時間区分を取得
		List<ScheduleTimeEntity> scheduleTimeList = selectScheduleTimeForYear(yearMonthDayArray[0]);
		// 指定された月をintで取得
		int month = Integer.parseInt(yearMonthDayArray[1]);
		// 指定された月に該当する月のユーザごとの1ヵ月の労働時間を計算
		String[] scheduleWorkTimeMonthArray = calcScheduleWorkTimeMonth(scheduleMonthList, scheduleTimeList.get(month - 1));
		// ユーザごとの1年の労働時間を計算
		String[] scheduleWorkTimeYearArray = calcScheduleWorkTimeYear(scheduleYearList, scheduleTimeList);

		// Beanにセット
		ScheduleReportBean scheduleReportBean = new ScheduleReportBean();
		scheduleReportBean.setYear(Integer.parseInt(yearMonthDayArray[0]));
		scheduleReportBean.setMonth(Integer.parseInt(yearMonthDayArray[1]));
		scheduleReportBean.setScheduleCountMonthList(scheduleCountMonthList);
		scheduleReportBean.setScheduleCountYearList(scheduleWorkCountYearList);
		scheduleReportBean.setScheduleTimeMonthArray(scheduleWorkTimeMonthArray);
		scheduleReportBean.setScheduleTimeYearArray(scheduleWorkTimeYearArray);
		return scheduleReportBean;
	}


	/**
	 * 月次確定スケジュール勤務時間計算処理
	 *
	 * <p>
	 * 確定スケジュールから1ヵ月のユーザごとの勤務時間を計算し、配列で返す<br>
	 * ユーザごとに時間フォーマット(HH.M)で格納される<br>
	 * ただし、スケジュール時間区分が未登録または登録済みのスケジュールがないときは全ての勤務時間が0となる
	 *
	 * @param scheduleMonthList ユーザ毎の1ヶ月の確定スケジュール
	 * @param scheduleTimeEntity スケジュールに該当するスケジュール時間区分
	 * @return String[] ユーザごとの勤務時間
	 */
	private String[] calcScheduleWorkTimeMonth(List<ScheduleUserNameDto> scheduleMonthList, ScheduleTimeEntity scheduleTimeEntity) {

		// 登録済みのスケジュールがないまたはスケジュール時間区分がないとき、要素0の配列を返す
		if (scheduleMonthList.isEmpty() || scheduleTimeEntity == null) {
			String[] scheduleWorkTime0Array = new String[0];
			return scheduleWorkTime0Array;
		}

		// ユーザごとの勤務時間を格納する変数
		String[] scheduleWorkTimeArray = new String[scheduleMonthList.size()];

		// それぞれのスケジュール時間区分から勤務時間を取得する
		Long[] workTimeMsArray = new CmnScheduleTimeLogic().calcWorkTimeMsArray(scheduleTimeEntity);

		// 共通Logicクラス
		CmnScheduleLogic cmnScheduleLogic = new CmnScheduleLogic();
		CommonLogic commonLogic = new CommonLogic();

		// 1ヵ月分の確定スケジュールに登録されているユーザだけループする
		for (int i = 0; i < scheduleMonthList.size(); i++) {

			// 現在のループ目の要素iのScheduleUserNameDtoを取得
			ScheduleUserNameDto scheduleUserNameDto = scheduleMonthList.get(i);

			// ユーザの勤務時間(ミリ秒)の合計を格納する変数
			long sumWorkTimeMs = 0;

			// 登録済みのスケジュールをListで取得
			List<String> scheduleList = scheduleUserNameDto.scheduleFormatScheduleDayList();

			// 日付ごとの登録済みのスケジュールだけループする
			for (String schedule: scheduleList) {

				// スケジュールが登録済みであるか判定した配列を取得
				Boolean[] isScheduleRecordedArray = cmnScheduleLogic.toIsScheduleArray(schedule, scheduleTimeEntity);

				// 判定したスケジュール情報だけループする
				for (int j = 0; j < isScheduleRecordedArray.length; j++) {

					// 該当のスケジュール時間区分にスケジュールが登録されているとき、該当の勤務時間をを追加する
					if (isScheduleRecordedArray[j] != null && isScheduleRecordedArray[j]) {
						sumWorkTimeMs += workTimeMsArray[j];
					}
				}
			}

			// 勤務時間の合計値を時間フォーマットに変換し、格納する
			scheduleWorkTimeArray[i] = commonLogic.changeTimeHour(sumWorkTimeMs);
		}
		return scheduleWorkTimeArray;
	}


	/**
	 * 年次確定スケジュール勤務時間計算処理
	 *
	 * <p>scheduleYearListに登録されている確定スケジュールから1年のユーザごとの勤務時間を計算し、Stringの配列で返す<br>
	 * ユーザごとに時間フォーマット(HH.M)で格納される<br>
	 * ただし、scheduleMonthListがEmpty(登録済みのスケジュールがない)ときは何もせず要素0のString[]が返される
	 * </p>
	 *
	 * @param scheduleYearList DBから取得したList<ScheduleYearDto> (List&lt;ScheduleYearDto&gt;)<br>
	 * ただし、Emptyのときは要素0のString[]が返される
	 * @param scheduleTimeEntityList DBから取得したList<ScheduleTimeEntity> (List&lt;ScheduleTimeEntity&gt;)<br>
	 * ただし、Emptyのときは要素0のString[]が返される
	 * @return String[] ユーザごとの勤務時間
	 */
	private String[] calcScheduleWorkTimeYear(List<ScheduleYearDto> scheduleYearList, List<ScheduleTimeEntity> scheduleTimeEntityList) {

		// 登録済みのスケジュールがないまたはスケジュール時間区分がないとき、要素0の配列を返す
		if (scheduleYearList.isEmpty() || scheduleTimeEntityList.isEmpty()) {
			String[] scheduleWorkTime0Array = new String[0];
			return scheduleWorkTime0Array;
		}

		// ユーザごとの勤務時間を格納する変数
		String[] scheduleWorkTimeArray = new String[scheduleYearList.size()];

		// それぞれの月とスケジュール時間区分から勤務時間を保持する配列 (要素: [月][該当月のスケジュール時間区分])
		Long[][] workTimeMsArray2 = new Long[scheduleTimeEntityList.size()][Const.SCHEDULE_RECORDABLE_MAX_DIVISION];

		// 共通Logicクラス
		CmnScheduleTimeLogic cmnScheduleTimeLogic = new CmnScheduleTimeLogic();
		CmnScheduleLogic cmnScheduleLogic = new CmnScheduleLogic();
		CommonLogic commonLogic = new CommonLogic();

		// 1年分のスケジュール時間区分だけループする
		for (int i = 0; i < scheduleTimeEntityList.size(); i++) {

			// それぞれのスケジュール時間区分の勤務時間(ミリ秒)を計算し、格納
			Long[] workTimeMsArray = cmnScheduleTimeLogic.calcWorkTimeMsArray(scheduleTimeEntityList.get(i));
			workTimeMsArray2[i] = workTimeMsArray;
		}

		// 1ヵ月分の確定スケジュールに登録されているユーザだけループする
		for (int i = 0; i < scheduleYearList.size(); i++) {

			//現在のループ目の要素iのScheduleUserNameDtoを取得
			ScheduleYearDto scheduleYearDto = scheduleYearList.get(i);

			//ユーザの勤務時間(ミリ秒)の合計を格納する変数
			long sumWorkTimeMs = 0;

			// 登録済みのスケジュールをListで取得
			List<String> scheduleList = scheduleYearDto.scheduleFormatScheduleDayList();

			// 日付ごとの登録済みのスケジュールだけループする
			for (String schedule: scheduleList) {

				//workTimeMsArray2の要素数(登録されている月の数)だけループする
				for (int j = 0; j < workTimeMsArray2.length; j++) {

					// 現在の月に該当するスケジュールの文字列を取得するために指定する文字列の桁数
					int startCharLength = j * Const.SCHEDULE_RECORDABLE_MAX_DIVISION;
					int endCharLength = (j + 1) * Const.SCHEDULE_RECORDABLE_MAX_DIVISION;

					// 該当月のスケジュールを取得
					String monthSchedule = "";

					if (schedule != null && endCharLength <= schedule.length()) {

						// 該当月のスケジュールが存在するとき、スケジュールを取得
						monthSchedule = schedule.substring(startCharLength, endCharLength);
					} else {

						// 該当月のスケジュールがないとき、スケジュールが未登録を取得
						for (int k = 1; k <= Const.SCHEDULE_RECORDABLE_MAX_DIVISION; k++) {
							monthSchedule += Const.SCHEDULE_NOT_RECORDED;
						}
					}


					// スケジュールが登録済みであるかどうかの判定した配列を取得
					Boolean[] isScheduleRecordedArray = cmnScheduleLogic.toIsScheduleArray(monthSchedule, scheduleTimeEntityList.get(j));

					// 1日ごとの判定したスケジュールの数だけループする
					for (int k = 0; k < isScheduleRecordedArray.length; k++) {

						// スケジュールが登録されているとき、スケジュールに対応する勤務時間をsumWorkTimeMsに追加する
						if (isScheduleRecordedArray[k] != null && isScheduleRecordedArray[k]) {
							sumWorkTimeMs += workTimeMsArray2[j][k];
						}
					}
				}
			}

			// 勤務時間の合計値を時間フォーマットに変換し、格納する
			scheduleWorkTimeArray[i] = commonLogic.changeTimeHour(sumWorkTimeMs);
		}
		return scheduleWorkTimeArray;
	}


	/**
	 * [Repository] 月次の勤務日数検索処理
	 *
	 * <p>
	 * 確定スケジュールから該当する年月の勤務日数をユーザ毎に取得する<br>
	 * 該当する年月にスケジュールが未登録でも該当する年にスケジュールが登録済みであれば、出勤日数がユーザ毎に取得される<br>
	 * ただし、該当する年にもスケジュールが登録されていないときはEmptyとなる
	 *
	 * @param ym 取得したい出勤日の年月
	 * @param year 取得したい出勤日の年月に該当する年<br>
	 * ただし、LIKE検索されるためフォーマットはYYYY%である必要がある
	 * @return List<ScheduleWorkCountYearDto> ユーザ毎の対象の年月の勤務日数
	 */
	private List<ScheduleCountMonthDto> selectScheduleCountMonth(String ym, String year) {

		// LIKEで検索されるため、年のフォーマットをYYYY%に変換
		String trimYear = year + Const.CHARACTER_PERCENT;
		return scheduleCountMonthRepository.selectScheduleCountMonth(ym, trimYear);
	}


	/**
	 * [Repository] 年次の勤務日数検索処理
	 *
	 * <p>
	 * 確定スケジュールから該当する年月の勤務日数をユーザ毎に取得する<br>
	 * 該当する年月にスケジュールが未登録でも該当する年にスケジュールが登録済みであれば、出勤日数がユーザ毎に取得される<br>
	 * ただし、該当する年にもスケジュールが登録されていないときはEmptyとなる
	 *
	 * @param ym 取得したい出勤日の年月
	 * @param year 取得したい出勤日の年月に該当する年<br>
	 * ただし、LIKE検索されるためフォーマットはYYYY%である必要がある
	 * @return List<ScheduleWorkCountYearDto> ユーザ毎の対象の年月の勤務日数
	 */
	private List<ScheduleCountYearDto> selectScheduleCountYear(String year) {

		// LIKEで検索されるため、年のフォーマットをYYYY%に変換
		String trimYear = year + Const.CHARACTER_PERCENT;
		return scheduleCountYearRepository.selectScheduleCountYear(trimYear);
	}


	/**
	 * [Repository] 1年分確定スケジュール検索処理
	 *
	 * <p>
	 * ユーザーごとの1年分の確定スケジュールを取得する<br>
	 * 1年分(1～12月)のスケジュールが結合されて取得される<br>
	 * ただし、1年間(1～12月)のうち登録済みのスケジュールが登録されていないときはEmptyとなる
	 *
	 * @param year 取得したいスケジュールの年
	 * @return ScheduleYearDto ユーザ毎の1年分確定スケジュール
	 */
	private List<ScheduleYearDto> selectScheduleYear(String year){

		// 取得したい年に対応する月(1～12月)を年月で取得
		String ym1 = year + "01";
		String ym2 = year + "02";
		String ym3 = year + "03";
		String ym4 = year + "04";
		String ym5 = year + "05";
		String ym6 = year + "06";
		String ym7 = year + "07";
		String ym8 = year + "08";
		String ym9 = year + "09";
		String ym10 = year + "10";
		String ym11 = year + "11";
		String ym12 = year + "12";

		// 対象の年を"YYYY%"に変換
		String trimYear = year + Const.CHARACTER_PERCENT;
		return scheduleYearRepository.selectScheduleYear(trimYear, ym1, ym2, ym3, ym4, ym5, ym6, ym7, ym8, ym9, ym10, ym11, ym12);
	}


	/**
	 * [Repository] 月次確定スケジュール登録済みユーザ検索処理
	 *
	 * <p>
	 * 年間で登録されているうち1ヶ月分の確定スケジュールに登録済みのユーザを取得する<br>
	 * 対象の年月にスケジュールが登録されていなくても、その年の一度でもスケジュールが登録されていれば取得される
	 * ただし、取得したい年月の年に登録済みのユーザが1人もないときはEmptyとなる
	 *
	 * @param ym 取得したい年月
	 * @param year 取得したい年月の年
	 * @return List<ScheduleUserNameDto> ユーザ毎の1年分の確定スケジュール
	 */
	private List<ScheduleUserNameDto> selectScheduleUserName(String ym, String year){

		//yearを"YYYY%"に変換
		String trimYear = year + Const.CHARACTER_PERCENT;

		// 未登録時に置換する未登録のスケジュール情報を取得
		String replaceNotRecord = "";
		for (int i = 1; i <= Const.SCHEDULE_RECORDABLE_MAX_DIVISION; i++) {
			replaceNotRecord += Const.SCHEDULE_NOT_RECORDED;
		}
		return scheduleUserNameRepository.selectScheduleUserNameForYear(ym, trimYear, replaceNotRecord);
	}


	/**
	 * [Repository] 1年分スケジュール時間区分検索処理
	 *
	 * <p>
	 * 取得したい年から1年分のスケジュール時間区分を取得する<br>
	 * また、現在日(ymd)に該当するスケジュール時間区分が複数登録されているときは最新のスケジュール時間区分が取得される<br>
	 * ただし、スケジュール時間区分が何も登録されていないときはnullとなる
	 *
	 * @param year 取得したいスケジュール時間区分の年
	 * @return List<ScheduleTimeEntity> 1年分のスケジュール時間区分
	 */
	private List<ScheduleTimeEntity> selectScheduleTimeForYear(String year) {

		// 共通Logicクラス
		CommonLogic commonLogic = new CommonLogic();

		// 取得したスケジュール時間区分を格納する変数
		List<ScheduleTimeEntity> scheduleTimeEntityList = new ArrayList<>();

		// 1年分(12ヶ月)だけループする
		for (int i = 1; i <= 12; i++) {

			// 現在のループから日付を取得
			String ymd = commonLogic.changeLastDateYmd(Integer.parseInt(year), i);

			// 取得した日付に該当するスケジュール時間区分を取得し、Listに格納
			ScheduleTimeEntity scheduleTimeEntity = scheduleTimeRepository.selectScheduleTime(ymd);
			scheduleTimeEntityList.add(scheduleTimeEntity);
		}
		return scheduleTimeEntityList;
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
		return scheduleTimeRepository.selectScheduleTime(ymd);
	}
}
