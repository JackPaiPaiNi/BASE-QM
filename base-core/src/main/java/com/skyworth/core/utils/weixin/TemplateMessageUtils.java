package com.skyworth.core.utils.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

/**
 * 微信公众号模板信息工具
 * 
 * @author 魏诚
 *
 */
public class TemplateMessageUtils {

	public final static String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN ";
	public final static String OAUTH2_GRANT = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5b7e7ad488a29445&redirect_uri="
			+"http://weixin2.95105555.com/wechat/oauth2?gotoPage=REDERECT_URI&response_type=code&scope=snsapi_base&state=1#wechat_redirect";

	public static void sendTemplateMessage(String openid, String xymc, String zdr, String cdbm, String qrlx) {
		// 取得token
		if (StringUtils.isBlank(CommonUtils.accessToken)) {
			Token token = CommonUtils.getToken();
			if (token == null) {
				System.out.println("获取token失败！");
			} else {
				CommonUtils.accessToken = token.getAccessToken();
			}
		}
		String title = "您有一条新的返利政策事项未处理";
		String remark = "有新的返利政策推出，请于两日内进行确认，超期未确认政策会自动作废，经您确认生效后我司方可及时兑付返利。";
		// 生成消息json字符串
		String sendDATA = createSendMessage(title, remark, openid, xymc, zdr, cdbm, qrlx, null);
		// 发送post请求URL
		String sendURL = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", CommonUtils.accessToken);
		// 发起POST请求发送消息
		JSONObject jsonObject = CommonUtils.httpsRequest(sendURL, "POST", sendDATA);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				System.out.println("模板消息发送成功！");
			} else if (41006 == errorCode || 42001 == errorCode || 40001 == errorCode) {
				// access_token超时,重新再发一次
				CommonUtils.accessToken = null;
				sendTemplateMessage(openid, xymc, zdr, cdbm, qrlx);
			} else {
				System.out.println("模板消息发送失败！" + errorMsg);
			}
		}
	}

	public static void sendTemplateMessageApprove(String openid, String xymc, String zdr, String cdbm, String qrlx) {
		// 取得token
		if (StringUtils.isBlank(CommonUtils.accessToken)) {
			Token token = CommonUtils.getToken();
			if (token == null) {
				System.out.println("获取token失败！");
			} else {
				CommonUtils.accessToken = token.getAccessToken();
			}
		}
		String url = "/weixin/approve/list";
		String title = "您有一条待审批的单据！";
		String remark = "温馨提示：“超级老板”系统中有需要您审批的返利政策，您可以直接点击该通知审批也可以登录网址：http://fl.skyallhere.com/，账号、密码与OA系统相同。";
		// 生成消息json字符串
		String sendDATA = createSendMessage(title, remark, openid, xymc, zdr, cdbm, qrlx, url);
		// 发送post请求URL
		String sendURL = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", CommonUtils.accessToken);
		// 发起POST请求发送消息
		JSONObject jsonObject = CommonUtils.httpsRequest(sendURL, "POST", sendDATA);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				System.out.println("模板消息发送成功！");
			} else if (41006 == errorCode || 42001 == errorCode || 40001 == errorCode) {
				// access_token超时,重新再发一次
				CommonUtils.accessToken = null;
				sendTemplateMessageApprove(openid, xymc, zdr, cdbm, qrlx);
			} else {
				System.out.println("模板消息发送失败！" + errorMsg);
			}
		}
	}

	/**
	 * 生成模板消息数据json字符串
	 * 
	 * @param openid
	 * @param first标题
	 * @param keyword1协议名称
	 * @param keyword2制单人
	 * @param keyword3承担部门
	 * @param keyword4审批完成时间
	 * @param keyword5政策确认类型
	 * @param remark底部提示信息
	 * @return
	 */
	private static String createSendMessage(String title, String remark, String openid, String xymc, String zdr,
			String cdbm, String qrlx, String url) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String spsj = sdf.format(new Date());
		StringBuilder sb = new StringBuilder();

		sb.append("{\"touser\":\"" + openid + "\",");
		sb.append("\"template_id\":\"m94UU2tOfLs2oPSTxP9UMMYh_9SoxRGk6Z-0_1-V4DY\",");
		if(!StringUtils.isBlank(url)){
			sb.append("\"url\":\"" + OAUTH2_GRANT.replace("REDERECT_URI", url) + "\",");
		}
		sb.append("\"topcolor\":\"#FF0000\",");
		sb.append("\"data\":{");
		sb.append("\"first\": {\"value\":\"" + title + "\",\"color\":\"#173177\"},");
		sb.append("\"keyword1\":{\"value\":\"" + xymc + "\",\"color\":\"#173177\"},");
		sb.append("\"keyword2\":{\"value\":\"" + zdr + "\",\"color\":\"#173177\"},");
		sb.append("\"keyword3\":{\"value\":\"" + cdbm + "\",\"color\":\"#173177\"},");
		sb.append("\"keyword4\":{\"value\":\"" + spsj + "\",\"color\":\"#173177\"},");
		sb.append("\"keyword5\":{\"value\":\"" + qrlx + "\",\"color\":\"#FF0000\"},");
		sb.append("\"remark\":{\"value\":\"" + remark + "\",\"color\":\"#173177\"}");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 测试main方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateMessageUtils.sendTemplateMessage("oJyEF1FzROKBg9rDPh3rn_kfMzZM", "国美2018年度规模返利政策", "王歌", "彩电事业本部",
				"生效确认");
	}

}
