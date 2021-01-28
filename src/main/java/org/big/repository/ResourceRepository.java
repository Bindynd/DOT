package org.big.repository;

import org.big.entity.Resource;
import org.big.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p><b>Resource的DAO类接口</b></p>
 *<p> Resource的DAO类接口，与Resource有关的持久化操作方法</p>
 * @author BINZI
 *<p>Created date: 2018/08/23 13:35</p>
 *<p>Copyright: The Research Group of Biodiversity Informatics (BiodInfo Group) - 中国科学院动物研究所生物多样性信息学研究组</p>
 * @version: 0.1
 * @since JDK 1.80_144
 */
@Repository
public interface ResourceRepository extends BaseRepository<Resource, String> {

	/**
	 * <b>带分页的keyitem下的特征图片列表查询</b>
     *<p> 带分页的keyitem下的特征图片列表查询</p>
	 * @author MengMeng (王孟豪)
	 * @param pageable
	 * @param keyitemId
	 * @return
	 */
	@Query(value = "Select re from Resource re where re.keyitem.id = ?1")
	Page<Resource> findFeatureImgList(Pageable pageable, String keyitemId);

	/**
     *<b>通过Id查找一个实体</b>
     *<p> 通过Id查找一个实体</p>
     * @author MengMeng (王孟豪)
     * @param id
     * @return org.big.entity.Resource
     */
	@Query(value = "Select re from Resource re where re.id = ?1")
	Resource findOneById(String id);

	/**
     *<b>通过Id删除一个实体</b>
     *<p> 通过Id删除一个实体</p>
     * @author MengMeng (王孟豪)
     * @param id
     */
	@Modifying
	@Transactional
	@Query("Delete Resource re where re.id =?1")
	void deleteOneById(String id);
	/**
	 * @Description 根据检索表Id删除检索表图片
	 * @param taxkeyId
	 */
	@Modifying
	@Transactional
	@Query(value = "delete from resource where keyitem_id in("
			+ "select id from keyitem where taxkey_id = ?1))", nativeQuery = true)
	void delResourceBYTaxkeyId(String taxkeyId);
	/**
	 * @Description 根据taxonId删除检索表图片
	 * @param taxonId
	 */
	@Modifying
	@Transactional
	@Query(value = "delete from resource where keyitem_id in("
			+ "select id from keyitem where taxkey_id in("
			+ "select id from taxkey where taxon_id = ?1))", nativeQuery = true)
	void delResourceByTaxonId(String taxonId);
	
}
