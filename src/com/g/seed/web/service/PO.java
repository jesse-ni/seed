package com.g.seed.web.service;

import com.g.seed.web.Encrypt;
import com.g.seed.web.Optional;
import com.google.gson.GsonBuilder;

/**
 * @author zhigeng.ni
 *
 */
public class PO {

	public static class BaseNParamObject {

		/** 商家编号 */
		@Encrypt
		public String shopID = "";

		/** 省ID */
		public String provinceID = "22";

		/** 地级市ID */
		public String cityID;

		/** 区（县级市）ID */
		public String districtID;

		/** 设备ID */
		@Encrypt
		public String deviceID;

		public BaseNParamObject(String shopID, String provinceID,
				String cityID, String districtID, String deviceID) {
			super();
			this.shopID = shopID;
			this.provinceID = provinceID;
			this.cityID = cityID;
			this.districtID = districtID;
			this.deviceID = deviceID;
		}

		public BaseNParamObject() {
		}

		@Override
		public String toString() {
			return new GsonBuilder().serializeNulls().create().toJson(this);
		}

	}

	public static class POLogin extends BaseNParamObject {
		@Encrypt
		public String userPhone;
		@Encrypt
		public String userCode;
		@Encrypt
		public String uptime;

		public POLogin(String userPhone, String userCode, String uptime) {
			this.userPhone = userPhone;
			this.userCode = userCode;
			this.uptime = uptime;
		}

	}

	/**
	 * 获取抽奖所需信息
	 * 
	 * @author zhigeng.ni
	 *
	 */
	public static class PORaffleInfo extends BaseNParamObject {

		/** 用户手机号码 */
		@Encrypt
		public String userPhone;

		public PORaffleInfo(String userPhone) {
			this.userPhone = userPhone;
		}

	}

	/**
	 * 进行抽奖
	 * 
	 * @author zhigeng.ni
	 *
	 */
	public static class PORaffle extends BaseNParamObject {

		/** 用户手机号码 */
		@Encrypt
		public String userPhone;

		public PORaffle(String userPhone) {
			this.userPhone = userPhone;
		}

	}

	public static class PORecharge extends BaseNParamObject {
		@Encrypt
		public Integer flag;
		@Encrypt
		public String userPhone;
		@Encrypt
		public String cardNum;
		@Encrypt
		public String cardPwd;
		@Encrypt
		public String uptime;
		public String code;

		public PORecharge(Integer flag, String userPhone,
				String cardNum, String cardPwd, String uptime, String code) {
			this.flag = flag;
			this.userPhone = userPhone;
			this.cardNum = cardNum;
			this.cardPwd = cardPwd;
			this.uptime = uptime;
			this.code = code;
		}
	}

	public static class POGetImage {
		public String mediaID;
		@Optional
		public Integer width;
		@Optional
		public Integer height;
		@Encrypt
		public String deviceID;

		public POGetImage(String mediaID, Integer width, Integer height, String deviceID) {
			this.mediaID = mediaID;
			this.width = width;
			this.height = height;
			this.deviceID = deviceID;
		}
	}

	public static class POGetCodeIMG extends BaseNParamObject {
		@Encrypt
		public String userPhone;

		public POGetCodeIMG(String userPhone) {
			this.userPhone = userPhone;
		}

	}

	public static class POSubmitOrder extends BaseNParamObject {

		/** 用户手机号码 */
		@Encrypt
		public String userPhone;

		/** 团购ID */
		@Encrypt
		public String groupID;

		/** 提供团购服务的商家的ID（接口7.1返回的ShopID） */
		@Encrypt
		public String keyID;

		/** 团购数量 */
		public Integer count;

		/** 提交时间 */
		@Encrypt
		public String uptime;

		public POSubmitOrder(String userPhone, String groupID, String keyID,
				Integer count, String uptime) {
			this.userPhone = userPhone;
			this.groupID = groupID;
			this.keyID = keyID;
			this.count = count;
			this.uptime = uptime;
		}

	}

	public static class POOrderPay extends BaseNParamObject {

		/** 支付方式：1.短信积分支付 2.手机令牌支付 3.pos刷卡支付 */
		@Encrypt
		public Integer flag;

		/** 用户手机号 */
		@Encrypt
		public String userPhone;

		/** 订单ID */
		@Encrypt
		public String orderID;

		/** 验证码 */
		@Encrypt
		public String code;

		/** 订单金额 */
		@Encrypt
		public Double money;

		/** 提交时间 */
		@Encrypt
		public String uptime;

		public POOrderPay() {
			// TODO Auto-generated constructor stub
		}

		public POOrderPay(Integer flag, String userPhone, String orderID,
				String code, Double money, String uptime) {
			this.flag = flag;
			this.userPhone = userPhone;
			this.orderID = orderID;
			this.code = code;
			this.money = money;
			this.uptime = uptime;
		}

	}

	public static class POOrderList extends BaseNParamObject {

		/** 筛选某状态的订单：0.全部 1.未付款 2.未使用 3.已使用 4.待领奖 */
		public Integer statusFlag;

		/** 获取第几页数据 */
		@Optional
		public Integer page;

		/** 返回的列表数据个数 */
		@Optional
		public Integer count;

		/** 排序依据：0.最新 1.评价 2.销量 3.价格 */
		@Optional
		public Integer sort;

		/** 排序方式：0升序 1降序 */
		@Optional
		public Integer sortDesc;

		/** 盖网用户编号 */
		@Encrypt
		public String userCode;

		public POOrderList(Integer statusFlag, Integer page, Integer count,
				Integer sort, Integer sortDesc, String userCode) {
			this.statusFlag = statusFlag;
			this.page = page;
			this.count = count;
			this.sort = sort;
			this.sortDesc = sortDesc;
			this.userCode = userCode;
		}

	}

	public static class POGrouponList extends BaseNParamObject {

		/** 搜索关键字 */
		@Optional
		public String key;

		/** 商家ID */
		@Optional
		public String keyID;

		/** 分类ID */
		@Optional
		public String typeID;

		/** 页码 */
		@Optional
		public Integer page;

		/** 返回的列表数据个数 */
		@Optional
		public Integer count;

		/** 排序依据：0.最新 1.评价 2.销量 3.价格 */
		@Optional
		public Integer sort;

		/** 排序方式：0.升序 1.降序 */
		@Optional
		public Integer sortDesc;

		public POGrouponList(String key, String keyID, String typeID,
				Integer page, Integer count, Integer sort, Integer sortDesc) {
			this.key = key;
			this.keyID = keyID;
			this.typeID = typeID;
			this.page = page;
			this.count = count;
			this.sort = sort;
			this.sortDesc = sortDesc;
		}

	}

	public static class POSellerList extends BaseNParamObject {

		/** 搜索关键字 */
		@Optional
		public String key;

		/** 商家ID */
		@Optional
		public String keyID;

		/** 分类ID */
		@Optional
		public String typeID;

		/** 页码 */
		@Optional
		public Integer page;

		/** 返回的列表数据个数 */
		@Optional
		public Integer count;

		/** 排序依据：0.最新 1.评价 2.销量 3.价格 */
		@Optional
		public Integer sort;

		/** 排序方式：0.升序 1.降序 */
		@Optional
		public Integer sortDesc;

		public POSellerList(String key, String keyID, String typeID,
				Integer page, Integer count, Integer sort, Integer sortDesc) {
			this.key = key;
			this.keyID = keyID;
			this.typeID = typeID;
			this.page = page;
			this.count = count;
			this.sort = sort;
			this.sortDesc = sortDesc;
		}

	}

	public static class POOrderDetail extends BaseNParamObject {

		/** 订单的ID */
		@Encrypt
		public String orderID;

		public POOrderDetail(String orderID) {
			this.orderID = orderID;
		}

	}

	public static class POPOGrouponDetail extends BaseNParamObject {

		/** 团购ID */
		@Encrypt
		public String key;

		/** 提供团购服务的商家的ID（接口7.1返回的ShopID） */
		@Encrypt
		public String keyID;

		public POPOGrouponDetail(String key, String keyID) {
			this.key = key;
			this.keyID = keyID;
		}

	}

	public static class POSendGrouponCode extends BaseNParamObject {

		/** 用户手机号 */
		@Encrypt
		public String userPhone;

		/** 团购码 */
		@Encrypt
		public String groupCode;

		public POSendGrouponCode(String userPhone, String groupCode) {
			this.userPhone = userPhone;
			this.groupCode = groupCode;
		}

	}

	public static class POOrderUse extends BaseNParamObject {
		public String franchiseeID;
		public String code;
		public String userPhone;

		public POOrderUse(String franchiseeID, String code, String userPhone) {
			this.franchiseeID = franchiseeID;
			this.code = code;
			this.userPhone = userPhone;
		}
	}

	public static class POGetReward extends BaseNParamObject {

		/** 团购消费码 */
		@Encrypt
		public String groupCode;

		/** 消费体验评分 */
		public Double consumptiveExperienceScore;

		/** 商家服务评分 */
		public Double shopServiceScore;

		/** 评论内容 */
		public String content;

		/** 提交时间 */
		@Encrypt
		public String upTime;

		/** 用户手机号码 */
		@Encrypt
		public String userPhone;

		public POGetReward(String groupCode, Double consumptiveExperienceScore,
				Double shopServiceScore, String content, String upTime,
				String userPhone) {
			this.groupCode = groupCode;
			this.consumptiveExperienceScore = consumptiveExperienceScore;
			this.shopServiceScore = shopServiceScore;
			this.content = content;
			this.upTime = upTime;
			this.userPhone = userPhone;
		}

	}

	public static class POGetSellerDetail extends BaseNParamObject {

		/** 商家ID */
		@Encrypt
		public String key;

		public POGetSellerDetail(String key) {
			this.key = key;
		}
	}

	public static class POGrouponComment extends BaseNParamObject {

		/** 团购ID */
		@Encrypt
		public String key;

		/** 页码 */
		@Optional
		public Integer page;

		/** pageSize */
		@Optional
		public Integer count;

		/** 排序依据（0.最新 1.评分值） */
		@Optional
		public Integer sort;

		/** 排序方式（0.升序 1.降序） */
		@Optional
		public Integer sortDesc;

		public POGrouponComment(String key, Integer page, Integer count,
				Integer sort, Integer sortDesc) {
			this.key = key;
			this.page = page;
			this.count = count;
			this.sort = sort;
			this.sortDesc = sortDesc;
		}

	}

	public static class POSellerComment extends BaseNParamObject {

		/** 商家ID */
		@Encrypt
		public String key;

		/** 页码 */
		@Optional
		public Integer page;

		/** pageSize */
		@Optional
		public Integer count;

		/** 排序依据（0.最新 1.评分值） */
		@Optional
		public Integer sort;

		/** 排序方式（0.升序 1.降序） */
		@Optional
		public Integer sortDesc;

		public POSellerComment(String key, Integer page, Integer count,
				Integer sort, Integer sortDesc) {
			this.key = key;
			this.page = page;
			this.count = count;
			this.sort = sort;
			this.sortDesc = sortDesc;
		}
	}

	public static class POGetCode extends BaseNParamObject {

		/** 用户手机号 */
		@Encrypt
		public String userPhone;

		public POGetCode(String userPhone) {
			this.userPhone = userPhone;
		}

	}

	/**
	 * 签到记录
	 * 
	 * @author zhigeng.ni
	 */
	public static class POSignInRecore extends BaseNParamObject {

		/** 用户手机号 */
		@Encrypt
		public String userPhone;

		public POSignInRecore(String userPhone) {
			this.userPhone = userPhone;
		}
	}

	/**
	 * 激活
	 * 
	 * @author zhigeng.ni
	 *
	 */
	public static class POActivation {

		/** 商家编号 */
		@Encrypt
		private String shopID;

		/** 广告机管理密码 */
		@Encrypt
		private String devicePassword;

		/** 激活码 */
		@Encrypt
		private String activeCode;

		/** 设备ID */
		@Encrypt
		private String deviceID;

		public POActivation(String shopID, String devicePassword, String activeCode, String deviceID) {
			this.shopID = shopID;
			this.devicePassword = devicePassword;
			this.activeCode = activeCode;
			this.deviceID = deviceID;
		}

	}

	/**
	 * 签到
	 * 
	 * @author zhigeng.ni
	 *
	 */
	public static class POSignin extends BaseNParamObject {

		/** 手机验证码 此参数与 MoblieToken必传一个 */
		@Encrypt
		@Optional
		public String checkCode;

		/** 用户手机号 */
		@Encrypt
		@Optional
		public String userPhone;

		/** 手机令牌 */
		@Encrypt
		@Optional
		public String moblieToken;

		/** 提交时间 */
		@Encrypt
		public String uptime;

	}

}
