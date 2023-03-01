package com.workManagement.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workManagement.domain.model.entity.ScheduleEntity;

/**
 * @author saito
 *
 */
@Repository
public interface ScheduleRepository extends BaseRepository<ScheduleEntity, Integer> {


	/**
	 * [Repository] 確定スケジュール検索処理
	 *
	 * <p>
	 * 年月とユーザIDと一致する確定スケジュールを取得する<br>
	 * ただし、該当のスケジュールがない場合はnullとなる
	 *
	 * @param ym 取得したいスケジュールの年月
	 * @param user 取得したいユーザのユーザID
	 * @return ScheduleEntity 確定スケジュール
	 */
	@Query(value = "SELECT s.* FROM schedule s WHERE s.ym = :ym AND s.user = :user", nativeQuery = true)
	public ScheduleEntity selectSchedule(String ym, String user);
}
