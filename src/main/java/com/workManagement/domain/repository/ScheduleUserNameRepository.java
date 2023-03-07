package com.workManagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workManagement.domain.model.dto.ScheduleUserNameDto;

/**
 * @author saito
 *
 */
@Repository
public interface ScheduleUserNameRepository extends BaseRepository<ScheduleUserNameDto, Integer> {


	/**
	 * [Repository] 確定スケジュール登録済みユーザ検索処理
	 *
	 * <p>
	 * 年月とユーザIDと一致する確定スケジュールに登録済みのユーザをユーザ名で全て取得する<br>
	 * ただし、登録済みのユーザが1人もないときはEmptyとなる
	 *
	 * @param ym 取得したいスケジュールの年月
	 * @return List<ScheduleUserNameDto> ユーザ毎の確定スケジュール
	 */
	@Query(value = "SELECT u.id, u.name AS user_name, s.day1, s.day2, s.day3, s.day4, s.day5, s.day6, s.day7, s.day8, s.day9, s.day10, s.day11, s.day12, s.day13, s.day14, s.day15, s.day16, s.day17, s.day18, s.day19, s.day20, s.day21, s.day22, s.day23, s.day24, s.day25, s.day26, s.day27, s.day28, s.day29, s.day30, s.day31 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym ORDER BY u.id", nativeQuery = true)
	public List<ScheduleUserNameDto> selectScheduleUserName(String ym);


	/**
	 * [Repository] 月次確定スケジュール登録済みユーザ検索処理
	 *
	 * <p>
	 * 年間で登録されているうち1ヶ月分の確定スケジュールに登録済みのユーザを取得する<br>
	 * 対象の年月にスケジュールが登録されていなくても、その年の一度でもスケジュールが登録されていれば取得される
	 * ただし、取得したい年月の年に登録済みのユーザが1人もないときはEmptyとなる
	 *
	 * @param ym 取得したい年月
	 * @param year 取得したい年月の年<br>
	 * ただし、LIKE検索されるため"YYYY%"でなければならない
	 * @param replaceNotRecord スケジュール未登録時に置換されるスケジュール未登録情報
	 * @return List<ScheduleUserNameDto> ユーザ毎の1年分の確定スケジュール
	 */
	@Query(value = "SELECT u.id, u.name AS user_name, (SELECT CASE COALESCE(SUM(s.day1),0) WHEN 0 THEN :replaceNotRecord ELSE s.day1 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day1, (SELECT CASE COALESCE(SUM(s.day2),0) WHEN 0 THEN :replaceNotRecord ELSE s.day2 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day2, (SELECT CASE COALESCE(SUM(s.day3),0) WHEN 0 THEN :replaceNotRecord ELSE s.day3 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day3, (SELECT CASE COALESCE(SUM(s.day4),0) WHEN 0 THEN :replaceNotRecord ELSE s.day4 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day4, (SELECT CASE COALESCE(SUM(s.day5),0) WHEN 0 THEN :replaceNotRecord ELSE s.day5 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day5, (SELECT CASE COALESCE(SUM(s.day6),0) WHEN 0 THEN :replaceNotRecord ELSE s.day6 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day6, (SELECT CASE COALESCE(SUM(s.day7),0) WHEN 0 THEN :replaceNotRecord ELSE s.day7 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day7, (SELECT CASE COALESCE(SUM(s.day8),0) WHEN 0 THEN :replaceNotRecord ELSE s.day8 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day8, (SELECT CASE COALESCE(SUM(s.day9),0) WHEN 0 THEN :replaceNotRecord ELSE s.day9 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day9, (SELECT CASE COALESCE(SUM(s.day10),0) WHEN 0 THEN :replaceNotRecord ELSE s.day10 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day10, (SELECT CASE COALESCE(SUM(s.day11),0) WHEN 0 THEN :replaceNotRecord ELSE s.day11 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day11, (SELECT CASE COALESCE(SUM(s.day12),0) WHEN 0 THEN :replaceNotRecord ELSE s.day12 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day12, (SELECT CASE COALESCE(SUM(s.day13),0) WHEN 0 THEN :replaceNotRecord ELSE s.day13 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day13, (SELECT CASE COALESCE(SUM(s.day14),0) WHEN 0 THEN :replaceNotRecord ELSE s.day14 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day14, (SELECT CASE COALESCE(SUM(s.day15),0) WHEN 0 THEN :replaceNotRecord ELSE s.day15 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day15, (SELECT CASE COALESCE(SUM(s.day16),0) WHEN 0 THEN :replaceNotRecord ELSE s.day16 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day16, (SELECT CASE COALESCE(SUM(s.day17),0) WHEN 0 THEN :replaceNotRecord ELSE s.day17 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day17, (SELECT CASE COALESCE(SUM(s.day18),0) WHEN 0 THEN :replaceNotRecord ELSE s.day18 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day18, (SELECT CASE COALESCE(SUM(s.day19),0) WHEN 0 THEN :replaceNotRecord ELSE s.day19 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day19, (SELECT CASE COALESCE(SUM(s.day20),0) WHEN 0 THEN :replaceNotRecord ELSE s.day20 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day20, (SELECT CASE COALESCE(SUM(s.day21),0) WHEN 0 THEN :replaceNotRecord ELSE s.day21 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day21, (SELECT CASE COALESCE(SUM(s.day22),0) WHEN 0 THEN :replaceNotRecord ELSE s.day22 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day22, (SELECT CASE COALESCE(SUM(s.day23),0) WHEN 0 THEN :replaceNotRecord ELSE s.day23 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day23, (SELECT CASE COALESCE(SUM(s.day24),0) WHEN 0 THEN :replaceNotRecord ELSE s.day24 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day24, (SELECT CASE COALESCE(SUM(s.day25),0) WHEN 0 THEN :replaceNotRecord ELSE s.day25 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day25, (SELECT CASE COALESCE(SUM(s.day26),0) WHEN 0 THEN :replaceNotRecord ELSE s.day26 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day26, (SELECT CASE COALESCE(SUM(s.day27),0) WHEN 0 THEN :replaceNotRecord ELSE s.day27 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day27, (SELECT CASE COALESCE(SUM(s.day28),0) WHEN 0 THEN :replaceNotRecord ELSE s.day28 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day28, (SELECT CASE COALESCE(SUM(s.day29),0) WHEN 0 THEN :replaceNotRecord ELSE s.day29 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day29, (SELECT CASE COALESCE(SUM(s.day30),0) WHEN 0 THEN :replaceNotRecord ELSE s.day30 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day30, (SELECT CASE COALESCE(SUM(s.day31),0) WHEN 0 THEN :replaceNotRecord ELSE s.day31 END FROM schedule s WHERE s.user = a.user AND s.ym = :ym) AS day31 FROM (SELECT DISTINCT s.user FROM schedule s WHERE s.ym LIKE :year) a INNER JOIN user u ON u.id = a.user", nativeQuery = true)
	public List<ScheduleUserNameDto> selectScheduleUserNameForYear(String ym, String year, String replaceNotRecord);
}
