package com.workManagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workManagement.domain.model.dto.ScheduleDayDto;

/**
 * @author saito
 *
 */
@Repository
public interface ScheduleDayRepository extends BaseRepository<ScheduleDayDto, Integer> {


	/**
	 * [Repository] 1日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の1日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day1 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day1 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day1 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day1 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day1 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day1 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day1 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day1 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay1(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 2日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の2日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day2 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day2 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day2 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day2 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day2 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day2 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day2 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day2 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay2(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 3日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の3日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day3 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day3 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day3 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day3 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day3 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day3 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day3 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day3 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay3(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);

	/**
	 * [Repository] 4日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の4日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day4 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day4 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day4 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day4 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day4 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day4 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day4 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day4 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay4(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 5日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の5日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day5 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day5 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day5 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day5 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day5 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day5 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day5 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day5 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay5(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 6日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の6日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day6 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day6 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day6 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day6 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day6 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day6 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day6 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day6 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay6(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 7日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の7日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day7 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day7 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day7 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day7 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day7 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day7 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day7 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day7 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay7(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 8日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の8日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day8 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day8 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day8 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day8 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day8 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day8 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day8 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day8 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay8(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 9日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の9日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day9 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day9 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day9 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day9 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day9 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day9 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day9 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day9 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay9(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 10日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の10日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day10 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day10 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day10 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day10 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day10 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day10 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day10 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day10 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay10(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 11日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の11日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day11 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day11 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day11 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day11 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day11 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day11 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day11 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day11 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay11(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 12日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の12日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day12 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day12 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day12 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day12 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day12 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day12 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day12 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day12 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay12(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 13日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の13日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day13 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day13 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day13 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day13 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day13 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day13 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day13 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day13 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay13(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 14日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の14日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day14 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day14 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day14 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day14 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day14 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day14 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day14 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day14 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay14(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 15日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の15日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day15 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day15 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day15 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day15 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day15 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day15 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day15 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day15 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay15(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 16日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の16日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day16 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day16 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day16 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day16 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day16 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day16 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day16 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day16 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay16(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 17日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の17日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day17 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day17 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day17 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day17 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day17 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day17 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day17 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day17 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay17(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 18日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の18日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day18 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day18 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day18 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day18 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day18 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day18 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day18 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day18 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay18(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 19日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の19日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day19 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day19 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day19 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day19 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day19 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day19 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day19 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day19 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay19(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 20日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の20日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day20 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day20 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day20 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day20 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day20 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day20 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day20 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day20 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay20(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 21日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の21日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day21 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day21 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day21 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day21 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day21 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day21 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day21 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day21 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay21(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 22日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の22日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day22 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day22 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day22 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day22 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day22 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day22 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day22 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day22 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay22(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 23日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の23日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day23 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day23 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day23 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day23 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day23 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day23 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day23 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day23 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay23(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 24日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の24日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day24 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day24 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day24 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day24 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day24 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day24 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day24 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day24 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay24(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 25日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の25日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day25 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day25 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day25 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day25 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day25 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day25 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day25 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day25 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay25(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 26日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の26日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day26 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day26 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day26 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day26 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day26 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day26 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day26 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day26 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay26(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 27日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の27日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day27 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day27 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day27 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day27 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day27 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day27 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day27 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day27 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay27(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 28日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の28日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day28 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day28 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day28 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day28 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day28 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day28 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day28 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day28 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay28(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 29日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の29日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day29 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day29 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day29 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day29 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day29 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day29 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day29 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day29 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay29(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 30日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の30日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月()
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 *
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day30 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day30 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day30 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day30 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day30 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day30 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day30 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day30 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay30(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);


	/**
	 * [Repository] 31日目の確定スケジュール取得処理
	 *
	 * <p>
	 * 対象の年月の31日に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される
	 *
	 * @param schedule dayのスケジュール検索時、絞り込みたい文字 (%1%)
	 * @param schedule1 dayのスケジュール時間1に登録されている検索したい1文字目 (%1______%)
	 * @param schedule2 dayのスケジュール時間2に登録されている検索したい2文字目 (%_1_____%)
	 * @param schedule3 dayのスケジュール時間3に登録されている検索したい3文字目 (%__1____%)
	 * @param schedule4 dayのスケジュール時間4に登録されている検索したい4文字目 (%___1___%)
	 * @param schedule5 dayのスケジュール時間5に登録されている検索したい5文字目 (%____1__%)
	 * @param schedule6 dayのスケジュール時間6に登録されている検索したい6文字目 (%_____1_%)
	 * @param schedule7 dayのスケジュール時間7に登録されている検索したい7文字目 (%______1%)
	 * @param replaceValue 検索した際に、一致したとき格納する値
	 * @param ym 検索したい年月
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	@Query(value = "SELECT s.id, u.id as user_id, u.name as user_name, CASE WHEN s.day31 LIKE :schedule1 THEN :replaceValue END AS schedule1, CASE WHEN s.day31 LIKE :schedule2 THEN :replaceValue END AS schedule2, CASE WHEN s.day31 LIKE :schedule3 THEN :replaceValue END AS schedule3, CASE WHEN s.day31 LIKE :schedule4 THEN :replaceValue END AS schedule4, CASE WHEN s.day31 LIKE :schedule5 THEN :replaceValue END AS schedule5, CASE WHEN s.day31 LIKE :schedule6 THEN :replaceValue END AS schedule6, CASE WHEN s.day31 LIKE :schedule7 THEN :replaceValue END AS schedule7 FROM schedule s INNER JOIN user u ON u.id = s.user WHERE s.ym = :ym AND s.day31 LIKE :schedule ORDER BY s.user", nativeQuery = true)
	public List<ScheduleDayDto> selectScheduleDay31(String schedule, String schedule1, String schedule2, String schedule3, String schedule4, String schedule5, String schedule6, String schedule7, String replaceValue, String ym);
}
