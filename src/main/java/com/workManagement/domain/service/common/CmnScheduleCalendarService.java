package com.workManagement.domain.service.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.logic.CommonLogic;
import com.workManagement.domain.model.bean.CmnScheduleCalendarBean;
import com.workManagement.domain.service.BaseService;

/**
 * @author saito
 *
 */
@Service
@Transactional
public class CmnScheduleCalendarService extends BaseService {

	/**
	 * [共通 Service] カレンダー、年月作成処理
	 *
	 * @param ym 年月
	 * @return CmnScheduleCalendarBean
	 */
	public CmnScheduleCalendarBean generateCalendarYmByYm(String ym) {

		// 年月をintの配列に変換
		int[] yearMonthArray = changeYearMonthArray(ym);
		// 最終日をymdで取得
		String lastDateYmd = new CommonLogic().changeLastDateYmd(yearMonthArray[0], yearMonthArray[1]);
		// カレンダーを取得
		List<Integer> calendarList = generateCalendar(yearMonthArray[0], yearMonthArray[1]);
		// 翌前月, 現在の月をymで取得
		String[] nowNextBeforeYmArray = calcNextBeforYmArray(yearMonthArray[0], yearMonthArray[1]);

		// Beanにセット
		CmnScheduleCalendarBean cmnScheduleCalendarBean = new CmnScheduleCalendarBean();
		cmnScheduleCalendarBean.setYear(yearMonthArray[0]);
		cmnScheduleCalendarBean.setMonth(yearMonthArray[1]);
		cmnScheduleCalendarBean.setLastDateYmd(lastDateYmd);
		cmnScheduleCalendarBean.setCalendarList(calendarList);
		cmnScheduleCalendarBean.setNowYm(nowNextBeforeYmArray[0]);
		cmnScheduleCalendarBean.setAfterYm(nowNextBeforeYmArray[1]);
		cmnScheduleCalendarBean.setBeforeYm(nowNextBeforeYmArray[2]);
		return cmnScheduleCalendarBean;
	}


	/**
	 * 翌前月に取得処理
	 *
	 * <p>
	 * 翌月と前月を計算して返す<br>
	 * ym(YYYYMM)に変換した現在の月[0], 翌月[1]と前月[2]
	 *
	 * @param year LocalDateから取得した年(int)
	 * @param month LocalDateから取得した月(int)
	 * @return String[] 現在、来月、前月の年月<br>
	 * String[0]が現在の月, String[1]が翌月, String[2]が前月
	 */
	private String[] calcNextBeforYmArray(int year, int month) {

		//year, monthから現在のLocalDateを取得し、nowYmに代入
		LocalDate nowLd = getLocalDateByYearMonth(year, month);
		int nowYear = nowLd.getYear();
		int nowMonth = nowLd.getMonthValue();

		// 共通ロジックをクラス
		CommonLogic commonLogic = new CommonLogic();

		// 現在の年月からym(YYYYMM)を取得
		String nowYm = commonLogic.changeYm(nowYear, nowMonth);

		// 現在の年月から前月の年月をym(YYYYMM)で取得
		LocalDate beforeMonthLd = nowLd.minusMonths(1);
		int beforeYear = beforeMonthLd.getYear();
		int beforeMonth = beforeMonthLd.getMonthValue();
		String beforeYm = commonLogic.changeYm(beforeYear, beforeMonth);

		// 現在の年月から来月の年月をym(YYYYMM)で取得
		LocalDate afterMonthLd = nowLd.plusMonths(1);
		int afterYear = afterMonthLd.getYear();
		int afterMonth = afterMonthLd.getMonthValue();
		String afterYm = commonLogic.changeYm(afterYear, afterMonth);

		// それぞれの年月を配列に格納し、返す
		String[] nowAfterBeforeYmArray = {nowYm, afterYm, beforeYm};
		return nowAfterBeforeYmArray;
	}


	/**
	 * 年月変換処理
	 *
	 * <p>
	 * 年と月をint型に変換し、intの配列に格納して返す<br>
	 * ただし、ymがnullまたはフォーマット通りでないときは現在の年月になる<br>
	 * int[0]が年, int[1]が月
	 *
	 * @param ym 年月
	 * @return int[] intに変換した年と月<br>
	 * int[0]が年, int[1]が月
	 */
	private int[] changeYearMonthArray(String ym) {

		// 年月をLocalDateで取得する
		String ymd = ym + "01";
		LocalDate ymdLd = new CommonLogic().getLocalDateByYmd(ymd);

		// 年月が指定されていないまたはymがYYYYMMでないとき
		if (ymdLd == null) {

			// 現在の日付を取得し、年月に変換
			LocalDate nowLd = LocalDate.now();
			int year = nowLd.getYear();
			int month = nowLd.getMonthValue();

			// 年月をint[]に格納して返す
			int[] yearMonthArray = {year, month};
			return yearMonthArray;
		}

		// ymdLdから年月に変換
		int year = ymdLd.getYear();
		int month = ymdLd.getMonthValue();

		// 年月をint[]に格納して返す
		int[] yearMonthArray = {year, month};
		return yearMonthArray;
	}


	/**
	 * カレンダー作成処理
	 *
	 * <p>
	 * 年, 月から1ヵ月分のカレンダーを作成する<br>
	 * ただし、カレンダーのフォーマット(7×4 or 7×5 or 7×6)にするため、前月, 翌月も含む(前月, 翌月の日付は含まれない)<br>
	 * また、前月, 翌月分の日付はnullが格納される
	 *
	 * @param year LocalDateから取得した年(int)
	 * @param month LocalDateから取得した月(int)
	 * @return List<Integer> 1ヵ月分のカレンダー
	 */
	private List<Integer> generateCalendar(int year, int month) {

		//------------------------------------
		// 第1週目の日曜日～初日までを設定
		//------------------------------------

		// 対象の年月の初日の日付をLocalDateで取得
		LocalDate localDate = getLocalDateByYearMonth(year, month);

		// 第1週目の初日の曜日を取得（月:1, 火:2.....日:7）
		int firstWeek = localDate.getDayOfWeek().getValue();

		// 日付けとスケジュールを格納
		List<Integer> calendarList = new ArrayList<>();

		// 初日の日付が日曜日ではないとき
		if (firstWeek != 7) {

			// 初日が日曜を除く取得した曜日の回数分nullを代入してカレンダーのフォーマットに揃える
			for (int i = 1; i <= firstWeek; i ++) {
				calendarList.add(null);
			}
		}

		//-------------
		// 日付を設定
		//-------------

		// 最終日をLocalDateから取得
		int lastDay = localDate.lengthOfMonth();

		// 最終日までループし、日付を格納
		for (int i = 1; i <= lastDay; i++) {
			calendarList.add(i);
		}

		//------------------------------------
		// 最終週の終了日～土曜日までを設定
		//------------------------------------

		// カレンダーから残りの最終週の土曜日までの日数を取得
		int remainderWeek = 7 - (calendarList.size() % 7);

		// 残りの日付が7(最終日が土曜日)でないとき
		if (remainderWeek != 7) {

			// 残りの日付の回数分nullを代入してカレンダーのフォーマットに揃える
			for (int i = 1; i <= remainderWeek; i ++) {
				calendarList.add(null);
			}
		}
		return calendarList;
	}


	/**
	 * [private 共通処理] LoccalDate取得処理
	 *
	 * <p>
	 * 年、月からLocalDateを返す<br>
	 * ただし、正確な日付は対象の年月の初日となる
	 *
	 * @param year 年
	 * @param month 月
	 * @return LocalDate 対象の年月のLocalDate
	 */
	private LocalDate getLocalDateByYearMonth(int year, int month) {
		return LocalDate.of(year, month, 1);
	}
}
