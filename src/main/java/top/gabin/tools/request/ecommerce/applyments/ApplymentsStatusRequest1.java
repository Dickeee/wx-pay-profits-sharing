package top.gabin.tools.request.ecommerce.applyments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * <pre>
 * 电商平台通过查询申请状态API查询二级商户入驻申请结果。该查询服务提供两种查询方式（两种查询方式返回结果一致）： 方式1：业务申请编号查询申请状态； 方式2：申请单号查询申请状态。
 * 文档地址:https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/ecommerce/applyments/chapter3_2.shtml
 * </pre>
 */
@Data
@JsonIgnoreProperties("outRequestNo")
public class ApplymentsStatusRequest1 {
	/**
	 * <pre>
	 * 字段名：业务申请编号
	 * 变量名：out_request_no
	 * 是否必填：是
	 * 类型：string[1,124]
	 * 描述：
	 *  path1、服务商自定义的商户唯一编号。
	 *  2、每个编号对应一个申请单，每个申请单审核通过后会生成一个微信支付商户号。
	 *  示例值：APPLYMENT_00000000001 
	 * </pre>
	 */
	@JsonIgnore
	@JsonProperty(value = "out_request_no")
	private String outRequestNo;

}