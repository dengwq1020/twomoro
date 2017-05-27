package com.xiyi.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xiyi.commons.utils.JsonUtils;

/**
 * 商品分类
 * @author Administrator
 *
 */
@TableName("b_product_type")
public class ProductType implements Serializable {
	
	
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/** 分类名称 */
	@TableField(value="name")
	private String name;
	
	/** 创建时间 */
	@TableField(value="create_time")
	private Date create_time;
	
	/** 修改时间 */
	@TableField(value="update_time")
	private Date update_time;
	
	/** 备注 */
	@TableField(value="description")
	private String description;
	
	/** 图片 */
	@TableField(value="img")
	private String img;
	
	/** 父ID */
	@TableField(value="pid")
	private Long pid;
	
	/** 状态 */
	@TableField(value="status")
	private byte  status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
