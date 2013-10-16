package com.yidejia.app.mall.jni;

/**
 * jni���ݽӿ�
 * @author long bin
 *
 */
public class JNICallBack {
	static {
		System.loadLibrary("Mall");
	}
	
	public static String HTTPURL = getHttp4PostUrl();
	
	//��ȡhost Url
	public static native String getHttp4PostUrl();
	
	//ͼƬ��ַ
	public static native String getHttp4ImageUrlPrefix();
	
	//ɾ���û��ջ���ַ
	public static native String getHttp4DelAddress(String cid, String aid, String token);
	
	//��ȡ�û��ջ���ַ
	public static native String getHttp4GetAddress(String where, String offset, String limit, String group, String order, String fields);
	
	//�����û��ջ���ַ
	public static native String getHttp4SaveAddress(String customer_id, String customer_name, String handset, String province,
			String city, String district, String address, String recipient_id, String token);
	
	//ɾ����Ʒ����
	public static native String getHttp4DelComment(String id);
	
	//��ȡ��Ʒ����
	public static native String getHttp4GetComment(String where, String offset, String limit, String group, String order, String fields);
	
	// ������Ʒ����
	public static native String getHttp4SaveComment(String goods_id,
			String user_id, String user_name, String title, String experience,
			String commentDate);
	
	//��ȡ��������
	public static native String getHttp4GetDistribute(String where, String offset, String limit, String group, String order, String fields);
	
	//��ȡ��������
	public static native String getHttp4GetExpress(String where, String offset, String limit, String group, String order, String fields);
	
	//��ȡ������Ϣ
	public static native String getHttp4GetFree(String where, String offset, String limit, String group, String order, String fields);
	
	//����ղ��Ƿ����
	public static native String getHttp4CheckFav(String userid, String goodsid);
	
	//ɾ���ղ�
	public static native String getHttp4DelFav(String userid, String goodsid, String token);
	
	//��ȡ�ղ��б�
	public static native String getHttp4GetFav(String where, String offset, String limit, String group, String order, String fields);
	
	//�����ղ�
	public static native String getHttp4SaveFav(String userid, String goodsid, String token);
	
	//��ȡ��Ʒ��Ϣ
	public static native String getHttp4GetGoods(String id);
	
	//��ȡ��ҳ
	public static native String getHttp4GetHome();
	
	//��ȡ��������
	public static native String getHttp4GetOrder(String user_id, String code, String date, String status, String offset1, String limit1, String token);
	
	//�޸�֧��״̬
	public static native String getHttp4PayOut(String customer_id, String code);
	
	//ȡ������
	public static native String getHttp4CancelOrder(String id, String code, String token);
	
	//ǩ�ն���
	public static native String getHttp4SignOrder(String id, String code, String token);
	
	// �ݽ�����
	public static native String getHttp4SaveOrder(String customer_id,
			String ticket_id, String recipient_id, String pingou_id,
			String goods_ascore, String ship_fee, String ship_type,
			String ship_entity_name, String goods_qty_scr, String comments,
			String token);

	//��ȡƷ��
	public static native String getHttp4GetBrand();
	
	//��ȡ��Ч
	public static native String getHttp4GetEffect(String where, String offset, String limit, String group, String order, String fields);
	
	//��ȡ�������
	public static native String getHttp4GetSearch(String name, String fun, String brand,String price, String order1, String offset1, String limit1);
	
	//��ȡ�۸�����
	public static native String getHttp4GetPrice();
	
	//��ȡ����
	public static native String getHttp4GetVoucher(String id, String token);
	
	//��¼
	public static native String getHttp4Login(String username, String password, String login_ip);
	
	//ע��
	public static native String getHttp4Register(String username, String password, String cps, String ip);
	
	//����Ĭ�ϵ�ַ
	public static native String getHttp4SetDefAddr(String cid, String aid, String token);
}
