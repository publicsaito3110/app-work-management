package com.workManagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workManagement.domain.model.dto.ScheduleCountYearDto;

/**
 * @author saito
 *
 */
@Repository
public interface ScheduleCountYearRepository extends BaseRepository<ScheduleCountYearDto, Integer> {


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
	 * @return List<ScheduleWorkCountYearDto> ユーザ毎の対象の年の勤務日数
	 */
	@Query(value = "SELECT u.id AS user_id, u.name AS user_name, (SELECT COUNT(CASE WHEN s.day1 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day2 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day3 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day4 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day5 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day6 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day7 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day8 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day9 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day10 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day11 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day12 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day13 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day14 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day15 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day16 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day17 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day18 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day19 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day20 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day21 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day22 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day23 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day24 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day25 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day26 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day27 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day28 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day29 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day30 LIKE '%1%' THEN 1 END) + COUNT(CASE WHEN s.day31 LIKE '%1%' THEN 1 END) FROM schedule s WHERE s.ym LIKE :year AND s.user = a.user) AS work_count FROM (SELECT DISTINCT s.user FROM schedule s WHERE s.ym LIKE :year) a INNER JOIN user u ON u.id = a.user", nativeQuery = true)
	public List<ScheduleCountYearDto> selectScheduleCountYear(String year);
}
