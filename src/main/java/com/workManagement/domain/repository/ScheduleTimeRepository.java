package com.workManagement.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workManagement.domain.model.entity.ScheduleTimeEntity;

/**
 * @author saito
 *
 */
@Repository
public interface ScheduleTimeRepository extends BaseRepository<ScheduleTimeEntity, Integer> {


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
	@Query(value = "SELECT s.* FROM schedule_time s WHERE s.end_ymd = (SELECT MIN(c.end_ymd) FROM schedule_time c WHERE :ymd <= c.end_ymd) AND s.id = (SELECT MAX(h.id) FROM schedule_time h WHERE h.end_ymd = (SELECT MIN(e.end_ymd) FROM schedule_time e WHERE :ymd <= e.end_ymd))", nativeQuery = true)
	public ScheduleTimeEntity selectScheduleTime(String ymd);
}
