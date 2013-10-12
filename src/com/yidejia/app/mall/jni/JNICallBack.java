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
	public static native String getHttp4SaveAddress();
	
	//ɾ����Ʒ����
	public static native String getHttp4DelComment();
	
	//��ȡ��Ʒ����
	public static native String getHttp4GetComment();
	
	//������Ʒ����
	public static native String getHttp4SaveComment();
	
	//��ȡ��������
	public static native String getHttp4GetDistribute();
	
	//��ȡ������Ϣ
	public static native String getHttp4GetFree();
	
	//����ղ��Ƿ����
	public static native String getHttp4CheckFav(String userid, String goodsid);
	
	//ɾ���ղ�
	public static native String getHttp4DelFav();
	
	//��ȡ�ղ��б�
	public static native String getHttp4GetFav();
	
	//�����ղ�
	public static native String getHttp4SaveFav();
	
	//��ȡ��Ʒ��Ϣ
	public static native String getHttp4GetGoods();
	
	//��ȡ��ҳ
	public static native String getHttp4GetHome();
	
	//��ȡ��������
	public static native String getHttp4GetOrder(String user_id, String code, String date, String status, String offset1, String limit1, String token);
	
	//�޸�֧��״̬
	public static native String getHttp4PayOut();
	
	//�ݽ�����
	public static native String getHttp4SaveOrder();
	
	//��ȡƷ��
	public static native String getHttp4GetBrand();
	
	//��ȡ��Ч
	public static native String getHttp4GetEffect();
	
	//��ȡ�������
	public static native String getHttp4GetSearch();
	
	//��ȡ�۸�����
	public static native String getHttp4GetPrice();
	
	//��ȡ����
	public static native String getHttp4GetVoucher();
}
