package top.gabin.tools.response.ecommerce.profitsharing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import top.gabin.tools.response.AbstractResponse;

import java.util.List;


/**
 * <pre>
 * 发起分账请求后，可调用此接口查询分账结果 ;发起分账完结请求后，可调用此接口查询分账完结的结果
 * 文档地址:https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/ecommerce/profitsharing/chapter3_2.shtml
 * 状态码	错误码	描述	解决方案
 * 500	SYSTEM_ERROR	系统错误	系统异常，请使用相同参数稍后重新调用
 * 400	PARAM_ERROR	参数错误	请使用正确的参数重新调用
 * 400	INVALID_REQUEST	参数错误	请使用正确的参数重新调用
 * 分账金额超限	分账金额不能大于可分金额或大于最大分账比例金额，请调整分账金额		
 * 分账接收方非法	分账接收方在分账之前需要进行添加		
 * 请求参数不符合参数格式	请求参数错误，检查原交易号是否存在或发起支付交易接口返回失败		
 * 订单处理中，暂时无法分账	订单处理中，暂时无法分账，请稍后再试		
 * 不是分账订单，无法分账	发起支付交易接口时请用分账的合适参数		
 * 429	FREQUENCY_LIMITED	频率限制	请降低频率后重试
 * 403	NO_AUTH	未开通分账权限	请开通商户号分账权限
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitSharingQueryApplyResponse extends AbstractResponse {
	/**
	 * <pre>
	 * 字段名：二级商户号
	 * 变量名：sub_mchid
	 * 是否必填：是
	 * 类型：string(32)
	 * 描述：
	 *  分账出资的电商平台二级商户，填写微信支付分配的商户号。 
	 *  示例值：1900000109 
	 * </pre>
	 */
	@JsonProperty(value = "sub_mchid")
	private String subMchid;

	/**
	 * <pre>
	 * 字段名：微信订单号
	 * 变量名：transaction_id
	 * 是否必填：是
	 * 类型：string(32)
	 * 描述：
	 *  微信支付订单号。 
	 *  示例值： 4208450740201411110007820472 
	 * </pre>
	 */
	@JsonProperty(value = "transaction_id")
	private String transactionId;

	/**
	 * <pre>
	 * 字段名：商户分账单号
	 * 变量名：out_order_no
	 * 是否必填：是
	 * 类型：string(64)
	 * 描述：
	 *  商户系统内部的分账单号，在商户系统内部唯一（单次分账、多次分账、完结分账应使用不同的商户分账单号），同一分账单号多次请求等同一次。 
	 *  示例值：P20150806125346 
	 * </pre>
	 */
	@JsonProperty(value = "out_order_no")
	private String outOrderNo;

	/**
	 * <pre>
	 * 字段名：微信分账单号
	 * 变量名：order_id
	 * 是否必填：是
	 * 类型：string(64)
	 * 描述：
	 *  微信分账单号，微信系统返回的唯一标识 
	 *  示例值： 008450740201411110007820472 
	 * </pre>
	 */
	@JsonProperty(value = "order_id")
	private String orderId;

	/**
	 * <pre>
	 * 字段名：分账单状态
	 * 变量名：status
	 * 是否必填：是
	 * 类型：string(32)
	 * 描述：
	 *  分账单状态，枚举值： 
	 *  ACCEPTED：受理成功 
	 *  PROCESSING：处理中 
	 *  FINISHED：分账成功 
	 *  CLOSED：处理失败，已关单 
	 *  示例值：FINISHED 
	 * </pre>
	 */
	@JsonProperty(value = "status")
	private String status;

	/**
	 * <pre>
	 * 字段名：+分账接收方列表
	 * 变量名：receivers
	 * 是否必填：是
	 * 类型：array
	 * 描述：分账接收方列表。 
	 * </pre>
	 */
	@JsonProperty(value = "receivers")
	private List<Receivers> receivers;

	/**
	 * <pre>
	 * 字段名：关单原因
	 * 变量名：close_reason
	 * 是否必填：否
	 * 类型：string(32)
	 * 描述：
	 *  关单原因描述，枚举值： 
	 *  NO_AUTH：分账授权已解除 
	 *  示例值：NO_AUTH 
	 * </pre>
	 */
	@JsonProperty(value = "close_reason")
	private String closeReason;

	/**
	 * <pre>
	 * 字段名：分账完结金额
	 * 变量名：finish_amount
	 * 是否必填：否
	 * 类型：int
	 * 描述：
	 *  分账完结的分账金额，单位为分， 仅当查询分账完结的执行结果时，存在本字段。 
	 *  示例值：100 
	 * </pre>
	 */
	@JsonProperty(value = "finish_amount")
	private Integer finishAmount;

	/**
	 * <pre>
	 * 字段名：分账完结描述
	 * 变量名：finish_description
	 * 是否必填：否
	 * 类型：string(80)
	 * 描述：
	 *  分账完结的原因描述，仅当查询分账完结的执行结果时，存在本字段。 
	 *  示例值：分账完结 
	 * </pre>
	 */
	@JsonProperty(value = "finish_description")
	private String finishDescription;

	public String getSubMchid() {
		return this.subMchid;
	}

	public void setSubMchid(String subMchid) {
		this.subMchid = subMchid;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutOrderNo() {
		return this.outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Receivers> getReceivers() {
		return this.receivers;
	}

	public void setReceivers(List<Receivers> receivers) {
		this.receivers = receivers;
	}

	public String getCloseReason() {
		return this.closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public Integer getFinishAmount() {
		return this.finishAmount;
	}

	public void setFinishAmount(Integer finishAmount) {
		this.finishAmount = finishAmount;
	}

	public String getFinishDescription() {
		return this.finishDescription;
	}

	public void setFinishDescription(String finishDescription) {
		this.finishDescription = finishDescription;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Receivers {
		/**
		 * <pre>
		 * 字段名：分账接受商户号
		 * 变量名：receiver_mchid
		 * 是否必填：是
		 * 类型：string(32)
		 * 描述：
		 *  填写微信支付分配的商户号，仅支持通过添加分账接收方接口添加的接收方；电商平台商户已默认添加到分账接收方，无需重复添加。 
		 *  示例值：1900000109 
		 * </pre>
		 */
		@JsonProperty(value = "receiver_mchid")
		private String receiverMchid;

		/**
		 * <pre>
		 * 字段名：分账金额
		 * 变量名：amount
		 * 是否必填：是
		 * 类型：int
		 * 描述：
		 *  分账金额，单位为分，只能为整数，不能超过原订单支付金额及最大分账比例金额。 
		 *  示例值： 4208450740201411110007820472 
		 * </pre>
		 */
		@JsonProperty(value = "amount")
		private Integer amount;

		/**
		 * <pre>
		 * 字段名：分账描述
		 * 变量名：description
		 * 是否必填：是
		 * 类型：string(80)
		 * 描述：
		 *  分账的原因描述，分账账单中需要体现。 
		 *  示例值：分帐1900000110 
		 * </pre>
		 */
		@JsonProperty(value = "description")
		private String description;

		/**
		 * <pre>
		 * 字段名：分账结果
		 * 变量名：result
		 * 是否必填：是
		 * 类型：string(32)
		 * 描述：
		 *  分账结果，枚举值： 
		 *  PENDING：待分账 
		 *  SUCCESS：分账成功 
		 *  ADJUST：分账失败待调账 
		 *  RETURNED：已转回分账方 
		 *  CLOSED：已关闭 
		 *  示例值：SUCCESS 
		 * </pre>
		 */
		@JsonProperty(value = "result")
		private String result;

		/**
		 * <pre>
		 * 字段名：完成时间
		 * 变量名：finish_time
		 * 是否必填：是
		 * 类型：string(64)
		 * 描述：
		 *  分账完成时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss:sss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss:sss表示时分秒毫秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35.120+08:00表示，北京时间2015年5月20日 13点29分35秒。
		 *  示例值： 2015-05-20T13:29:35.120+08:00 
		 * </pre>
		 */
		@JsonProperty(value = "finish_time")
		private String finishTime;

		/**
		 * <pre>
		 * 字段名：分账失败原因
		 * 变量名：fail_reason
		 * 是否必填：否
		 * 类型：string(32)
		 * 描述：
		 *  分账失败原因，枚举值：
		 *  ACCOUNT_ABNORMAL：分账接收账户异常 
		 *  NO_RELATION：分账关系已解除 
		 *  RECEIVER_HIGH_RISK：高风险接收方
		 *  示例值：NO_RELATION 
		 * </pre>
		 */
		@JsonProperty(value = "fail_reason")
		private String failReason;

		public String getReceiverMchid() {
			return this.receiverMchid;
		}

		public void setReceiverMchid(String receiverMchid) {
			this.receiverMchid = receiverMchid;
		}

		public Integer getAmount() {
			return this.amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getResult() {
			return this.result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getFinishTime() {
			return this.finishTime;
		}

		public void setFinishTime(String finishTime) {
			this.finishTime = finishTime;
		}

		public String getFailReason() {
			return this.failReason;
		}

		public void setFailReason(String failReason) {
			this.failReason = failReason;
		}

	}

}