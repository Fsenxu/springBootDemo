package com.heqichao.springBootDemo.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.heqichao.springBootDemo.base.entity.Equipment;
import com.heqichao.springBootDemo.base.entity.User;

/**
 * @author Muzzy Xu.
 * 
 * 
 */
public interface EquipmentMapper {
	
	@Select("SELECT id,eid,type,amount,range,total,alarms,e_status,online_time,remark,own_id"
			+ " FROM equipment where id = #{id}  and STATUS = 'Y' ")
	public Equipment getEquipmentById(@Param("id") Integer id);
	
	@Select("SELECT eid FROM equipment where own_id = #{uid}  and STATUS = 'Y' ")
	public List<String> getUserEquipmentIdList(@Param("uid") Integer uid);
	
	@Select("SELECT eid FROM equipment where STATUS = 'Y' ")
	public List<String> getEquipmentIdListAll();
	
	@Select("<script>SELECT id,eid,e_type,IFNULL(amount,0) as amount,e_range,"
			+ "(select count(1) from lightning_log  where devEUI = equipment.eid) as total,"
			+ "IFNULL(alarms,0) as alarms,e_status,online_time,remark,own_id"
			+ " FROM equipment where STATUS = 'Y'  "
			+ "<if test=\"competence == 3 \"> and own_id = #{id}  </if>"
			+ "<if test=\"competence == 4 \"> and own_id = #{parentId}  </if>"
			+ " </script>")
	public List<Equipment> getEquipments(User user);
	
	@Insert("insert into equipment (eid,e_type,amount,e_range,total,alarms,e_status,online_time,remark,own_id,status,update_uid)"
			+ " values(#{eid},#{eType},#{amount},#{eRange},#{total},#{alarms},#{eStatus},sysdate(),#{remark},#{ownId},'Y',#{updateUid}) ")
	public int insertEquipment(Equipment equ);
	
	@Update("update user set company=#{company}, contact=#{contact}, phone=#{phone}, fax=#{fax}, email=#{email}, site=#{site}, remark=#{remark}, update_time = sysdate(), update_uid = #{id} where id=#{id} and STATUS = 'Y' ")
	public int updateUserInfo(User user);
	
	@Update("update equipment set  update_time = sysdate(), update_uid = #{udid}, STATUS = 'N' where id=#{id} and STATUS = 'Y' ")
	public int delEquById(@Param("id")Integer eid,@Param("udid")Integer udid);

}
