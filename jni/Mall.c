#include <jni.h>
#include "md5.h"
#include <stdlib.h>
#include <time.h>
#include <memory.h>
#ifdef __cplusplus
extern "C" {
#endif

static const char *url = "http://192.168.1.254:802/";
//static const char *url = "http://fw1.atido.net/";

const char *pHead = "&key=fw_mobile&format=array&ts=";
//const char *strTemp = "ChunTianfw_mobile@SDF!TD#DF#*CB$GER@";
const char *strTemp = "ChunTianfw_mobile123456";

#define LEN 1024

void addString(char* desc, const char* addition) {
//	int len = strlen(desc);
//	int addLen = strlen(addition);
//	memcpy(desc+len, (void*)addition, addLen);
	strcat(desc, addition);
//	strcpy(desc, addition);
}

jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4PostUrl(JNIEnv* env,
		jobject thiz){
	return (*env)->NewStringUTF(env, url);
}
//ͼƬ��ַ get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4ImageUrlPrefix(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="?api=common.img.get";

	addString(urlString, url);
	addString(urlString, api);
	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "common.img.get");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//ɾ���û��ջ���ַ, ���� String cid , aid, token, ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4DelAddress(JNIEnv* env,
		jobject thiz, jstring cid, jstring aid, jstring token){
	const char *chcid = (*env)->GetStringUTFChars(env, cid, NULL);
	const char *chaid = (*env)->GetStringUTFChars(env, aid, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=ucenter.address.delete";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&cid=");
	addString(urlString, chcid);

	addString(urlString, "&aid=");
	addString(urlString, chaid);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.address.delete");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ�û��ջ���ַ, ���� where, offset, limit, group, order, fields,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetAddress(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	const char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	const char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	const char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	const char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	const char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	const char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=ucenter.address.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	addString(urlString, chorder);

	addString(urlString, "&fields=");
	addString(urlString, chfields);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.address.getList");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//�����û��ջ���ַ, ����cid, cname, handset, province, city, district, address, rid, token �� ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveAddress(JNIEnv* env,
		jobject thiz, jstring customer_id, jstring customer_name, jstring handset, jstring province,
		jstring city, jstring district, jstring address, jstring recipient_id, jstring token){

	const char *chcid = (*env)->GetStringUTFChars(env, customer_id, NULL);
	const char *chcname = (*env)->GetStringUTFChars(env, customer_name, NULL);
	const char *chhandset = (*env)->GetStringUTFChars(env, handset, NULL);
	const char *chprovince = (*env)->GetStringUTFChars(env, province, NULL);
	const char *chcity = (*env)->GetStringUTFChars(env, city, NULL);
	const char *chdistrict = (*env)->GetStringUTFChars(env, district, NULL);
	const char *chaddress = (*env)->GetStringUTFChars(env, address, NULL);
	const char *chrid = (*env)->GetStringUTFChars(env, recipient_id, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="api=ucenter.address.save";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	addString(urlString, chcid);

	addString(urlString, "&customer_name=");
	addString(urlString, chcname);

	addString(urlString, "&handset=");
	addString(urlString, chhandset);

	addString(urlString, "&province=");
	addString(urlString, chprovince);

	addString(urlString, "&city=");
	addString(urlString, chcity);

	addString(urlString, "&district=");
	addString(urlString, chdistrict);

	addString(urlString, "&address=");
	addString(urlString, chaddress);

	addString(urlString, "&recipient_id=");
	addString(urlString, chrid);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.address.save");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//ɾ���û�����, ���� String id�� ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4DelComment(JNIEnv* env,
		jobject thiz, jstring id){
	const char *chcid = (*env)->GetStringUTFChars(env, id, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=product.comments.delete";
	addString(urlString, api);

	addString(urlString, "&id=");
	addString(urlString, chcid);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.comments.delete");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ�û�����, ���� where, offset, limit, group, order, fields,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetComment(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	const char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	const char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	const char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	const char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	const char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	const char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=product.comments.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	addString(urlString, chorder);

	addString(urlString, "&fields=");
	addString(urlString, chfields);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.comments.getList");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//�����û�����, ����String goods_id,
//String user_id, String user_name, String title, String experience,
//String commentDate�� ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveComment(
		JNIEnv* env, jobject thiz, jstring goods_id, jstring user_id,
		jstring user_name, jstring title, jstring experience, jstring commentDate) {

	const char *chgoods_id = (*env)->GetStringUTFChars(env, goods_id, NULL);
	const char *chuser_id = (*env)->GetStringUTFChars(env, user_id, NULL);
	const char *chuser_name = (*env)->GetStringUTFChars(env, user_name, NULL);
	const char *chtitle = (*env)->GetStringUTFChars(env, title, NULL);
	const char *chex = (*env)->GetStringUTFChars(env, experience, NULL);
	const char *chdate = (*env)->GetStringUTFChars(env, commentDate, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="api=product.comments.save";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&goods_id=");
	addString(urlString, chgoods_id);
	addString(urlString, "&user_id=");
	addString(urlString, chuser_id);
	addString(urlString, "&user_name=");
	addString(urlString, chuser_name);
	addString(urlString, "&title=");
	addString(urlString, chtitle);
	addString(urlString, "&experience=");
	addString(urlString, chex);
	addString(urlString, "&commentDate=");
	addString(urlString, chdate);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.comments.save");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ��������, ���� where, offset, limit, group, order, fields,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetDistribute(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	const char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	const char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	const char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	const char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	const char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	const char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=ship.center.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	addString(urlString, chorder);

	addString(urlString, "&fields=");
	addString(urlString, chfields);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ship.center.getList");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ���ͷ���, ���� where, offset, limit, group, order, fields,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetExpress(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	const char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	const char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	const char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	const char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	const char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	const char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=ship.area.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	addString(urlString, chorder);

	addString(urlString, "&fields=");
	addString(urlString, chfields);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ship.area.getList");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ�����б�, ���� where, offset, limit, group, order, fields,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetFree(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	const char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	const char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	const char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	const char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	const char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	const char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=ship.free.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	addString(urlString, chorder);

	addString(urlString, "&fields=");
	addString(urlString, chfields);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ship.free.getList");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

/**
 * ����ղ��Ƿ���� ,���� String userid, String goodsid, post
 */
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4CheckFav(JNIEnv* env,
		jobject thiz, jstring userid, jstring goodsid){
	const char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	const char *chgid = (*env)->GetStringUTFChars(env, goodsid, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=product.favoliten.checkExists";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&userId=");
	addString(urlString, chuid);

	addString(urlString, "&goods_id=");
	addString(urlString, chgid);


	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.favoliten.checkExists");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//ɾ���ղ�, ���� String userid , goodsid, token, ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4DelFav(JNIEnv* env,
		jobject thiz, jstring userid, jstring goodsid, jstring token){
	const char *chcid = (*env)->GetStringUTFChars(env, userid, NULL);
	const char *chgid = (*env)->GetStringUTFChars(env, goodsid, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=product.favoliten.deleteByUg";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&userid=");
	addString(urlString, chcid);

	addString(urlString, "&goods_id=");
	addString(urlString, chgid);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.favoliten.deleteByUg");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ�ղ��б�, ���� where, offset, limit, group, order, fields,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetFav(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	const char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	const char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	const char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	const char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	const char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	const char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=product.favoliten.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	addString(urlString, chorder);

	addString(urlString, "&fields=");
	addString(urlString, chfields);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.favoliten.getList");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//�����ղ�, ���� String userid , goodsid, token, ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveFav(JNIEnv* env,
		jobject thiz, jstring userid, jstring goodsid, jstring token){
	const char *chcid = (*env)->GetStringUTFChars(env, userid, NULL);
	const char *chgid = (*env)->GetStringUTFChars(env, goodsid, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=product.favoliten.save";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&userid=");
	addString(urlString, chcid);

	addString(urlString, "&goods_id=");
	addString(urlString, chgid);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.favoliten.save");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ��Ʒ��Ϣ, ���� String id�� ����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetGoods(JNIEnv* env,
		jobject thiz, jstring id){
	const char *chcid = (*env)->GetStringUTFChars(env, id, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "?api=product.mallgood.getCollect";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	addString(urlString, chcid);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.mallgood.getCollect");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ��ҳ get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetHome(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="?api=home.mobile.page";

	addString(urlString, url);
	addString(urlString, api);

//	addString(urlString, "&is_web=n");

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "home.mobile.page");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ��������, ���� user_id, code, date, status, offset1, fields��token,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetOrder(JNIEnv* env,
		jobject thiz, jstring user_id, jstring code, jstring date,
		jstring status, jstring offset1, jstring limit1, jstring token){

	const char *chuser_id = (*env)->GetStringUTFChars(env, user_id, NULL);
	const char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	const char *chdate = (*env)->GetStringUTFChars(env, date, NULL);
	const char *chstatus = (*env)->GetStringUTFChars(env, status, NULL);
	const char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
	const char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=ucenter.order.getOrders";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&user_id=");
	addString(urlString, chuser_id);

	addString(urlString, "&code=");
	addString(urlString, chcode);

	addString(urlString, "&date=");
	addString(urlString, chdate);

	addString(urlString, "&status=");
	addString(urlString, chstatus);

	addString(urlString, "&offset1=");
	addString(urlString, choffset1);

	addString(urlString, "&limit1=");
	addString(urlString, chlimit1);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.getOrders");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//�޸�֧��״̬, ���� String customer_id , code, token, ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4PayOut(JNIEnv* env,
		jobject thiz, jstring customer_id, jstring code){
	const char *chcid = (*env)->GetStringUTFChars(env, customer_id, NULL);
	const char *chgid = (*env)->GetStringUTFChars(env, code, NULL);
//	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=ucenter.order.payout";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	addString(urlString, chcid);

	addString(urlString, "&code=");
	addString(urlString, chgid);
//
//	addString(urlString, "&token=");
//	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.payout");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//ȡ������, ���� String id , code, token, ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4CancelOrder(JNIEnv* env,
		jobject thiz, jstring id, jstring code, jstring token){
	const char *chcid = (*env)->GetStringUTFChars(env, id, NULL);
	const char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=ucenter.order.cancel";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	addString(urlString, chcid);

	addString(urlString, "&code=");
	addString(urlString, chcode);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.cancel");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//ǩ�ն���, ���� String id , code, token, ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SignOrder(JNIEnv* env,
		jobject thiz, jstring id, jstring code, jstring token){
	const char *chcid = (*env)->GetStringUTFChars(env, id, NULL);
	const char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=ucenter.order.sign";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	addString(urlString, chcid);

	addString(urlString, "&code=");
	addString(urlString, chcode);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.sign");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//�ύ����, ���� customer_id , ticket_id,recipient_id,pingou_id, goods_ascore,ship_fee,
//ship_type , ship_entity_name, goods_qty_scr,comments, token, ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveOrder(JNIEnv* env,
		jobject thiz, jstring customer_id, jstring ticket_id, jstring recipient_id, jstring pingou_id, jstring goods_ascore,
		jstring ship_fee, jstring ship_type, jstring ship_entity_name, jstring goods_qty_scr, jstring comments, jstring token){
	const char *chcustomer_id = (*env)->GetStringUTFChars(env, customer_id, NULL);
	const char *chticket_id = (*env)->GetStringUTFChars(env, ticket_id, NULL);
	const char *chrecipient_id = (*env)->GetStringUTFChars(env, recipient_id, NULL);
	const char *chpingou_id = (*env)->GetStringUTFChars(env, pingou_id, NULL);
	const char *chgoods_ascore = (*env)->GetStringUTFChars(env, goods_ascore, NULL);
	const char *chship_fee = (*env)->GetStringUTFChars(env, ship_fee, NULL);
	const char *chship_type = (*env)->GetStringUTFChars(env, ship_type, NULL);
	const char *chship_entity_name = (*env)->GetStringUTFChars(env, ship_entity_name, NULL);
	const char *chgoods_qty_scr = (*env)->GetStringUTFChars(env, goods_qty_scr, NULL);
	const char *chcomments = (*env)->GetStringUTFChars(env, comments, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api = "api=ucenter.order.save";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	addString(urlString, chcustomer_id);
	addString(urlString, "&ticket_id=");
	addString(urlString, chticket_id);
	addString(urlString, "&recipient_id=");
	addString(urlString, chrecipient_id);
	addString(urlString, "&pingou_id=");
	addString(urlString, chpingou_id);
	addString(urlString, "&goods_ascore=");
	addString(urlString, chgoods_ascore);
	addString(urlString, "&ship_fee=");
	addString(urlString, chship_fee);
	addString(urlString, "&ship_type=");
	addString(urlString, chship_type);
	addString(urlString, "&ship_entity_name=");
	addString(urlString, chship_entity_name);
	addString(urlString, "&goods_qty_scr=");
	addString(urlString, chgoods_qty_scr);
	addString(urlString, "&comments=");
	addString(urlString, chcomments);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.save");
	addString(encrypt, chtime);

//		char buf[32 + 1];
//		buf = retMd5String(encrypt);
	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;
	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡƷ�� get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetBrand(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="?api=product.mallgood.getBrands";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&desc=");

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.mallgood.getBrands");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ��Ч�б�, ���� where, offset, limit, group, order, fields,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetEffect(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	const char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	const char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	const char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	const char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	const char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	const char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=product.effect.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	addString(urlString, chorder);

	addString(urlString, "&fields=");
	addString(urlString, chfields);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.effect.getList");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ��������б�, ���� name, fun, brand, price, order1, offset1, limit1,����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetSearch(JNIEnv* env,
		jobject thiz, jstring name, jstring fun, jstring brand, jstring price, jstring order1, jstring offset1, jstring limit1){

	const char *chname = (*env)->GetStringUTFChars(env, name, NULL);
	const char *chfun = (*env)->GetStringUTFChars(env, fun, NULL);
	const char *chbrand = (*env)->GetStringUTFChars(env, brand, NULL);
	const char *chprice = (*env)->GetStringUTFChars(env, price, NULL);
	const char *chorder1 = (*env)->GetStringUTFChars(env, order1, NULL);
	const char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
	const char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	const char *api="?api=product.mallgood.search";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&name=");
	addString(urlString, chname);

	addString(urlString, "&fun=");
	addString(urlString, chfun);

	addString(urlString, "&brand=");
	addString(urlString, chbrand);

	addString(urlString, "&price=");
	addString(urlString, chprice);

	addString(urlString, "&order1=");
	addString(urlString, chorder1);

	addString(urlString, "&offset1=");
	addString(urlString, choffset1);

	addString(urlString, "&limit1=");
	addString(urlString, chlimit1);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.mallgood.search");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ�۸�����get
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetPrice(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="?api=product.mallgood.getPriceInterval";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&desc=");

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "product.mallgood.getPriceInterval");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ�û�����,����id�� token�� ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetVoucher(JNIEnv* env,
		jobject thiz, jstring id, jstring token){

	const char *chid = (*env)->GetStringUTFChars(env, id, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="api=user.info.get";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	addString(urlString, chid);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "user.info.get");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//��¼,����username, password, ip�� ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4Login(JNIEnv* env,
		jobject thiz, jstring username, jstring password, jstring ip){

	const char *chuser = (*env)->GetStringUTFChars(env, username, NULL);
	const char *chpsw = (*env)->GetStringUTFChars(env, password, NULL);
	const char *chip = (*env)->GetStringUTFChars(env, ip, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="api=user.passport.login";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&username=");
	addString(urlString, chuser);

	addString(urlString, "&password=");
	addString(urlString, chpsw);

	addString(urlString, "&login_ip=");
	addString(urlString, chip);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "user.passport.login");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//ע��,����username , password, cps, ip�� ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4Register(JNIEnv* env,
		jobject thiz, jstring username, jstring password, jstring cps, jstring ip){

	const char *chuser = (*env)->GetStringUTFChars(env, username, NULL);
	const char *chpsw = (*env)->GetStringUTFChars(env, password, NULL);
	const char *chcps = (*env)->GetStringUTFChars(env, cps, NULL);
	const char *chip = (*env)->GetStringUTFChars(env, ip, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="api=user.passport.register";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&username=");
	addString(urlString, chuser);

	addString(urlString, "&password=");
	addString(urlString, chpsw);

	addString(urlString, "&cps=");
	addString(urlString, chcps);

	addString(urlString, "&ip=");
	addString(urlString, chip);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "user.passport.register");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}

//����Ĭ�ϵ�ַ,����cid, aid�� token�� ����post
jstring Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SetDefAddr(JNIEnv* env,
		jobject thiz, jstring cid, jstring aid, jstring token){

	const char *chcid = (*env)->GetStringUTFChars(env, cid, NULL);
	const char *chaid = (*env)->GetStringUTFChars(env, aid, NULL);
	const char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	const char *api="api=ucenter.address.setDefault";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&cid=");
	addString(urlString, chcid);

	addString(urlString, "&aid=");
	addString(urlString, chaid);

	addString(urlString, "&token=");
	addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.address.setDefault");
	addString(encrypt, chtime);

	MD5_CTX md5;
	MD5Init(&md5);

	unsigned char decrypt[16];
	MD5Update(&md5, encrypt, strlen((char *) encrypt));
	MD5Final(&md5, decrypt);
	char buf[32 + 1];
	int i;
	for (i = 0; i < 16; i++) {
		sprintf(buf + i * 2, "%02x", decrypt[i]);
	}
	buf[32] = 0;

	addString(urlString, buf);

	return (*env)->NewStringUTF(env, urlString);
}





#ifdef __cplusplus
}
#endif