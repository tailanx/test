#include <jni.h>
#include "md5.h"
#include <stdlib.h>
#include <time.h>
#include <memory.h>
#include <stdio.h>
#ifdef __cplusplus
extern "C" {
#endif

//static char *url = "http://192.168.1.254/";
//char *strTemp = "ChunTianfw_mobile123456";
char *url = "http://fw1.atido.net/";
char *strTemp = "ChunTianfw_mobile@SDF!TD#DF#*CB$GER@";
char *urlTemp = "";

char *pHead = "&key=fw_mobile&format=array&ts=";

#define LEN 1024

void addString(char* desc, char* addition) {
//	int len = strlen(desc);
//	int addLen = strlen(addition);
//	memcpy(desc+len, (void*)addition, addLen);
	strcat(desc, addition);
//	strcpy(desc, addition);
}

void getParam(JNIEnv *env, jobject obj, const char* param){
//	jclass cls = (*env)->GetObjectClass(env, obj);
	jclass cls = (*env)->FindClass(env, "com/yidejia/app/mall/net/Param");
	if(cls != 0){
		jmethodID mid = (*env)->GetMethodID(env, cls, "<init>", "()V");
		jobject clsobj = (*env)->NewObject(env, cls, mid);
//		jfieldID fid = (*env)->GetFieldID(env, clsobj, "url", "Ljava/lang/String;");
//		jstring result = (jstring) (*env) ->GetObjectField(env, clsobj, fid);
//		param = (*env)->GetStringUTFChars(env, result, NULL);/**/
		if(clsobj == 0) return;
		jmethodID methodId = (*env)->GetMethodID(env,clsobj,"hosturl", "()Ljava/lang/String;");
		if(methodId != 0){
			jstring result = (jstring) (*env)->CallObjectMethod(env, clsobj, methodId);
			urlTemp = (*env)->GetStringUTFChars(env, result, NULL);

//			if(param == NULL) param = "hello";
		}
		/**/
	}
}

jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4PostUrl(JNIEnv* env,
		jobject thiz){
//	getParam(env, thiz, urlTemp);
	return (*env)->NewStringUTF(env, url);
}
//ͼƬ��ַ get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4ImageUrlPrefix(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="?api=common.img.get";

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4DelAddress(JNIEnv* env,
		jobject thiz, jstring cid, jstring aid, jstring token){
	char *chcid = (*env)->GetStringUTFChars(env, cid, NULL);
	char *chaid = (*env)->GetStringUTFChars(env, aid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=ucenter.address.delete";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&cid=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&aid=");
	if(chaid != NULL)addString(urlString, chaid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetAddress(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ucenter.address.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	if(chwhere != NULL)
		addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
//	addString(urlString, "+and+valid_flag%3D%27y%27&option%5Boffset%5D=");
	if(choffset != NULL)addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	if(chlimit != NULL)addString(urlString, chlimit);

//	addString(urlString, "&option%5Bgroup%5D=");
//	if(chgroup != NULL)addString(urlString, chgroup);
//
//	addString(urlString, "&option%5Border%5D=");
//	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, "&fields=%2A");
	if(chfields != NULL)addString(urlString, chfields);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveAddress(JNIEnv* env,
		jobject thiz, jstring customer_id, jstring customer_name, jstring handset, jstring province,
		jstring city, jstring district, jstring address, jstring recipient_id, jstring token){

	char *chcid = (*env)->GetStringUTFChars(env, customer_id, NULL);
	char *chcname = (*env)->GetStringUTFChars(env, customer_name, NULL);
	char *chhandset = (*env)->GetStringUTFChars(env, handset, NULL);
	char *chprovince = (*env)->GetStringUTFChars(env, province, NULL);
	char *chcity = (*env)->GetStringUTFChars(env, city, NULL);
	char *chdistrict = (*env)->GetStringUTFChars(env, district, NULL);
	char *chaddress = (*env)->GetStringUTFChars(env, address, NULL);
	char *chrid = (*env)->GetStringUTFChars(env, recipient_id, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="api=ucenter.address.save";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&customer_name=");
	if(chcname != NULL)addString(urlString, chcname);

	addString(urlString, "&handset=");
	if(chhandset != NULL)addString(urlString, chhandset);

	addString(urlString, "&province=");
	if(chprovince != NULL)addString(urlString, chprovince);

	addString(urlString, "&city=");
	if(chcity != NULL)addString(urlString, chcity);

	addString(urlString, "&district=");
	if(chdistrict != NULL)addString(urlString, chdistrict);

	addString(urlString, "&address=");
	if(chaddress != NULL)addString(urlString, chaddress);

	addString(urlString, "&recipient_id=");
	if(chrid != NULL)addString(urlString, chrid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4DelComment(JNIEnv* env,
		jobject thiz, jstring id){
	char *chcid = (*env)->GetStringUTFChars(env, id, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=product.comments.delete";
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chcid != NULL)addString(urlString, chcid);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetComment(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=product.comments.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	if(chwhere != NULL)addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	if(choffset != NULL)addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	if(chlimit != NULL)addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	if(chgroup != NULL)addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, "&fields=");
	if(chfields != NULL)addString(urlString, chfields);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveComment(
		JNIEnv* env, jobject thiz, jstring goods_id, jstring user_id,
		jstring user_name, jstring title, jstring experience, jstring commentDate) {

	char *chgoods_id = (*env)->GetStringUTFChars(env, goods_id, NULL);
	char *chuser_id = (*env)->GetStringUTFChars(env, user_id, NULL);
	char *chuser_name = (*env)->GetStringUTFChars(env, user_name, NULL);
	char *chtitle = (*env)->GetStringUTFChars(env, title, NULL);
	char *chex = (*env)->GetStringUTFChars(env, experience, NULL);
	char *chdate = (*env)->GetStringUTFChars(env, commentDate, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="api=product.comments.save";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&goods_id=");
	if(chgoods_id != NULL)addString(urlString, chgoods_id);
	addString(urlString, "&user_id=");
	if(chuser_id != NULL)addString(urlString, chuser_id);
	addString(urlString, "&user_name=");
	if(chuser_name != NULL)addString(urlString, chuser_name);
	addString(urlString, "&title=");
	if(chtitle != NULL)addString(urlString, chtitle);
	addString(urlString, "&experience=");
	if(chex != NULL)addString(urlString, chex);
	addString(urlString, "&commentDate=");
	if(chdate != NULL)addString(urlString, chdate);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetDistribute(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ship.center.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	if(chwhere != NULL)addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	if(choffset != NULL)addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	if(chlimit != NULL)addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	if(chgroup != NULL)addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, "&fields=");
	if(chfields != NULL)addString(urlString, chfields);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetExpress(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ship.area.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	if(chwhere != NULL)addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	if(choffset != NULL)addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	if(chlimit != NULL)addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	if(chgroup != NULL)addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, "&fields=");
	if(chfields != NULL)addString(urlString, chfields);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetFree(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ship.free.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	if(chwhere != NULL)addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	if(choffset != NULL)addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	if(chlimit != NULL)addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	if(chgroup != NULL)addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, "&fields=");
	if(chfields != NULL)addString(urlString, chfields);

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
 * ����ղ��Ƿ���� ,���� String userid, String goodsid, string token, post
 */
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4CheckFav(JNIEnv* env,
		jobject thiz, jstring userid, jstring goodsid, jstring token){
	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chgid = (*env)->GetStringUTFChars(env, goodsid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=product.favoliten.checkExists";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&userId=");
	if(chuid != NULL)addString(urlString, chuid);

	addString(urlString, "&goods_id=");
	if(chgid != NULL)addString(urlString, chgid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4DelFav(JNIEnv* env,
		jobject thiz, jstring userid, jstring goodsid, jstring token){
	char *chcid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chgid = (*env)->GetStringUTFChars(env, goodsid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=product.favoliten.deleteByUg";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&userid=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&goods_id=");
	if(chgid != NULL)addString(urlString, chgid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetFav(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=product.favoliten.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	if(chwhere != NULL)addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	if(choffset != NULL)addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	if(chlimit != NULL)addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	if(chgroup != NULL)addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, "&fields=");
	if(chfields != NULL)addString(urlString, chfields);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveFav(JNIEnv* env,
		jobject thiz, jstring userid, jstring goodsid, jstring token){
	char *chcid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chgid = (*env)->GetStringUTFChars(env, goodsid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=product.favoliten.save";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&userid=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&goods_id=");
	if(chgid != NULL)addString(urlString, chgid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetGoods(JNIEnv* env,
		jobject thiz, jstring id){
	char *chcid = (*env)->GetStringUTFChars(env, id, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "?api=product.mallgood.getCollect";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chcid != NULL)addString(urlString, chcid);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetHome(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	char *hostUrl;
	encrypt[0] = 0;
	urlString[0] = 0;
//	hostUrl[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));
//	getParam(env, thiz, hostUrl);

	char *api="?api=home.mobile.page";

//	if(hostUrl != NULL)
//		addString(urlString, hostUrl);
//	else
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

//��ȡ�������, ���� user_id, code, date, status, offset1, fields��token,����get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetOrder(JNIEnv* env,
		jobject thiz, jstring user_id, jstring code, jstring date,
		jstring status, jstring offset1, jstring limit1, jstring token){

	char *chuser_id = (*env)->GetStringUTFChars(env, user_id, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	char *chdate = (*env)->GetStringUTFChars(env, date, NULL);
	char *chstatus = (*env)->GetStringUTFChars(env, status, NULL);
	char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
	char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ucenter.order.getOrders";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&user_id=");
	if(chuser_id != NULL)addString(urlString, chuser_id);

	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

	addString(urlString, "&date=");
	if(chdate != NULL)addString(urlString, chdate);

	addString(urlString, "&status=");
	if(chstatus != NULL)addString(urlString, chstatus);

	addString(urlString, "&offset1=");
	if(choffset1 != NULL)addString(urlString, choffset1);

	addString(urlString, "&limit1=");
	if(chlimit1 != NULL)addString(urlString, chlimit1);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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

//根据订单编号获取订单信息, code ！方法，get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetOrderByCode(JNIEnv* env,
		jobject thiz, jstring code){

//	char *chuser_id = (*env)->GetStringUTFChars(env, user_id, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
//	char *chdate = (*env)->GetStringUTFChars(env, date, NULL);
//	char *chstatus = (*env)->GetStringUTFChars(env, status, NULL);
//	char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
//	char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ucenter.order.getByCode";

	addString(urlString, url);
	addString(urlString, api);

//	addString(urlString, "&user_id=");
//	if(chuser_id != NULL)addString(urlString, chuser_id);

	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

//	addString(urlString, "&date=");
//	if(chdate != NULL)addString(urlString, chdate);
//
//	addString(urlString, "&status=");
//	if(chstatus != NULL)addString(urlString, chstatus);
//
//	addString(urlString, "&offset1=");
//	if(choffset1 != NULL)addString(urlString, choffset1);
//
//	addString(urlString, "&limit1=");
//	if(chlimit1 != NULL)addString(urlString, chlimit1);
//
//	addString(urlString, "&token=");
//	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.getByCode");
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

//获取待评价商品列表 ,id ! get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetNoEvaluate(JNIEnv* env,
		jobject thiz, jstring user_id){

	char *chuser_id = (*env)->GetStringUTFChars(env, user_id, NULL);
//	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
//	char *chdate = (*env)->GetStringUTFChars(env, date, NULL);
//	char *chstatus = (*env)->GetStringUTFChars(env, status, NULL);
//	char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
//	char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ucenter.order.getNoEvaluate";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chuser_id != NULL)addString(urlString, chuser_id);

//	addString(urlString, "&code=");
//	if(chcode != NULL)addString(urlString, chcode);

//	addString(urlString, "&date=");
//	if(chdate != NULL)addString(urlString, chdate);
//
//	addString(urlString, "&status=");
//	if(chstatus != NULL)addString(urlString, chstatus);
//
//	addString(urlString, "&offset1=");
//	if(choffset1 != NULL)addString(urlString, choffset1);
//
//	addString(urlString, "&limit1=");
//	if(chlimit1 != NULL)addString(urlString, chlimit1);
//
//	addString(urlString, "&token=");
//	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.getNoEvaluate");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4PayOut(JNIEnv* env,
		jobject thiz, jstring customer_id, jstring code){
	char *chcid = (*env)->GetStringUTFChars(env, customer_id, NULL);
	char *chgid = (*env)->GetStringUTFChars(env, code, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=ucenter.order.payout";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&code=");
	if(chgid != NULL)addString(urlString, chgid);
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

//ȡ��, ���� String id , code, token, ����post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4CancelOrder(JNIEnv* env,
		jobject thiz, jstring id, jstring code, jstring token){
	char *chcid = (*env)->GetStringUTFChars(env, id, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=ucenter.order.cancel";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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

//ȡ��, ���� String id , code, token, ����post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4DelOrder(JNIEnv* env,
		jobject thiz, jstring id, jstring code, jstring token){
	char *chcid = (*env)->GetStringUTFChars(env, id, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=ucenter.order.delete";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];
	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.delete");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SignOrder(JNIEnv* env,
		jobject thiz, jstring id, jstring code, jstring token){
	char *chcid = (*env)->GetStringUTFChars(env, id, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=ucenter.order.sign";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SaveOrder(JNIEnv* env,
		jobject thiz, jstring customer_id, jstring ticket_id, jstring recipient_id, jstring pingou_id, jstring goods_ascore,
		jstring ship_fee, jstring ship_type, jstring ship_entity_name, jstring goods_qty_scr, jstring comments, jstring pay_type, jstring token, jstring device){
	char *chcustomer_id = (*env)->GetStringUTFChars(env, customer_id, NULL);
	char *chticket_id = (*env)->GetStringUTFChars(env, ticket_id, NULL);
	char *chrecipient_id = (*env)->GetStringUTFChars(env, recipient_id, NULL);
	char *chpingou_id = (*env)->GetStringUTFChars(env, pingou_id, NULL);
	char *chgoods_ascore = (*env)->GetStringUTFChars(env, goods_ascore, NULL);
	char *chship_fee = (*env)->GetStringUTFChars(env, ship_fee, NULL);
	char *chship_type = (*env)->GetStringUTFChars(env, ship_type, NULL);
	char *chship_entity_name = (*env)->GetStringUTFChars(env, ship_entity_name, NULL);
	char *chgoods_qty_scr = (*env)->GetStringUTFChars(env, goods_qty_scr, NULL);
	char *chcomments = (*env)->GetStringUTFChars(env, comments, NULL);
	char *chpay_type = (*env)->GetStringUTFChars(env, pay_type, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);
	char *chdevice = (*env)->GetStringUTFChars(env, device, NULL);

	char encrypt[LEN], urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api = "api=ucenter.order.save";
//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	if(chcustomer_id != NULL)addString(urlString, chcustomer_id);
	addString(urlString, "&ticket_id=");
	if(chticket_id != NULL)addString(urlString, chticket_id);
	addString(urlString, "&recipient_id=");
	if(chrecipient_id != NULL)addString(urlString, chrecipient_id);
	addString(urlString, "&pingou_id=");
	if(chpingou_id != NULL)addString(urlString, chpingou_id);
	addString(urlString, "&goods_ascore=");
	if(chgoods_ascore != NULL)addString(urlString, chgoods_ascore);
	addString(urlString, "&ship_fee=");
	if(chship_fee != NULL)addString(urlString, chship_fee);
	addString(urlString, "&ship_type=");
	if(chship_type != NULL)addString(urlString, chship_type);
	addString(urlString, "&ship_entity_name=");
	if(chship_entity_name != NULL)addString(urlString, chship_entity_name);
	addString(urlString, "&goods_qty_scr=");
	if(chgoods_qty_scr != NULL)addString(urlString, chgoods_qty_scr);
	addString(urlString, "&comments=");
	if(chcomments != NULL)addString(urlString, chcomments);
	addString(urlString, "&pay_type=");
	if(chpay_type != NULL)addString(urlString, chpay_type);
	addString(urlString, "&device_type=");
	if(chdevice != NULL)addString(urlString, chdevice);
	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetBrand(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="?api=product.mallgood.getBrands";

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetEffect(JNIEnv* env,
		jobject thiz, jstring where, jstring offset, jstring limit, jstring group, jstring order, jstring fields){

	char *chwhere = (*env)->GetStringUTFChars(env, where, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chgroup = (*env)->GetStringUTFChars(env, group, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, order, NULL);
	char *chfields = (*env)->GetStringUTFChars(env, fields, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=product.effect.getList";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&where=");
	if(chwhere != NULL)addString(urlString, chwhere);

	addString(urlString, "&option%5Boffset%5D=");
	if(choffset != NULL)addString(urlString, choffset);

	addString(urlString, "&option%5Blimit%5D=");
	if(chlimit != NULL)addString(urlString, chlimit);

	addString(urlString, "&option%5Bgroup%5D=");
	if(chgroup != NULL)addString(urlString, chgroup);

	addString(urlString, "&option%5Border%5D=");
	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, "&fields=");
	if(chfields != NULL)addString(urlString, chfields);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetSearch(JNIEnv* env,
		jobject thiz, jstring name, jstring fun, jstring brand, jstring price, jstring order1, jstring offset1, jstring limit1){

	char *chname = (*env)->GetStringUTFChars(env, name, NULL);
	char *chfun = (*env)->GetStringUTFChars(env, fun, NULL);
	char *chbrand = (*env)->GetStringUTFChars(env, brand, NULL);
	char *chprice = (*env)->GetStringUTFChars(env, price, NULL);
	char *chorder1 = (*env)->GetStringUTFChars(env, order1, NULL);
	char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
	char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);

	char encrypt[LEN] , urlString[LEN];

	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=product.mallgood.search";

	addString(urlString, url);
	addString(urlString, api);
	addString(urlString, "&name=");
	if(chname != NULL){
		addString(urlString, chname);
	}
	addString(urlString, "&fun=");
	if(chfun != NULL){
		addString(urlString, chfun);
	}
	addString(urlString, "&brand=");
	if(chname != NULL){
		addString(urlString, chbrand);
	}
	addString(urlString, "&price=");
	if(chprice != NULL)
		addString(urlString, chprice);

	addString(urlString, "&order1=");
	if(chorder1 != NULL)
		addString(urlString, chorder1);

	addString(urlString, "&offset1=");
	if(choffset1 != NULL)
		addString(urlString, choffset1);

	addString(urlString, "&limit1=");
	if(chlimit1 != NULL)
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
	int urllen = strlen((char *) urlString);
	urlString[urllen] = 0;
	return (*env)->NewStringUTF(env, urlString);
}

//��ȡ�۸����get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetPrice(JNIEnv* env,
		jobject thiz){
	int i;

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="?api=product.mallgood.getPriceInterval";

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

//��ȡ�û����,����id�� token�� ����post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetVoucher(JNIEnv* env,
		jobject thiz, jstring id, jstring token){

	char *chid = (*env)->GetStringUTFChars(env, id, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=user.info.get";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chid != NULL)addString(urlString, chid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4Login(JNIEnv* env,
		jobject thiz, jstring username, jstring password, jstring ip){

	char *chuser = (*env)->GetStringUTFChars(env, username, NULL);
	char *chpsw = (*env)->GetStringUTFChars(env, password, NULL);
	char *chip = (*env)->GetStringUTFChars(env, ip, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=user.passport.login";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&username=");
	if(chuser != NULL)addString(urlString, chuser);

	addString(urlString, "&password=");
	if(chpsw != NULL)addString(urlString, chpsw);

	addString(urlString, "&login_ip=");
	if(chip != NULL)addString(urlString, chip);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4Register(JNIEnv* env,
		jobject thiz, jstring username, jstring password, jstring cps, jstring ip){

	char *chuser = (*env)->GetStringUTFChars(env, username, NULL);
	char *chpsw = (*env)->GetStringUTFChars(env, password, NULL);
	char *chcps = (*env)->GetStringUTFChars(env, cps, NULL);
	char *chip = (*env)->GetStringUTFChars(env, ip, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=user.passport.register";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&username=");
	if(chuser != NULL)addString(urlString, chuser);

	addString(urlString, "&password=");
	if(chpsw != NULL)addString(urlString, chpsw);

	addString(urlString, "&cps=");
	if(chcps != NULL)addString(urlString, chcps);

	addString(urlString, "&ip=");
	if(chip != NULL)addString(urlString, chip);

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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SetDefAddr(JNIEnv* env,
		jobject thiz, jstring cid, jstring aid, jstring token){

	char *chcid = (*env)->GetStringUTFChars(env, cid, NULL);
	char *chaid = (*env)->GetStringUTFChars(env, aid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=ucenter.address.setDefault";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&cid=");
	if(chcid != NULL)addString(urlString, chcid);

	addString(urlString, "&aid=");
	if(chaid != NULL)addString(urlString, chaid);

	addString(urlString, "&token=");
	if(chaid != NULL)addString(urlString, chtoken);

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

//��ȡ����ͺͻ�ֻ����б� ����goods, userid ,post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetVerify(JNIEnv* env,
		jobject thiz, jstring goods, jstring userid){//, jstring token

	char *chgoods = (*env)->GetStringUTFChars(env, goods, NULL);
	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=active.verify.getList";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&goods=");
	if(chgoods != NULL)addString(urlString, chgoods);

	addString(urlString, "&user_id=");
	if(chuid != NULL)addString(urlString, chuid);

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
	addString(encrypt, "active.verify.getList");
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

//��ȡ�û���֣��ղأ���Ϣ�� ��������, ���� userid, token,����get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetCount(JNIEnv* env,
		jobject thiz, jstring userId, jstring token){

	char *chname = (*env)->GetStringUTFChars(env, userId, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=user.info.getCount";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&user_id=");
	if(chname != NULL)addString(urlString, chname);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "user.info.getCount");
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

//��ȡ��Ϣ�����û���Ϣ�б�, ���� userid, token, offset1,limit1,����get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetMessage(JNIEnv* env,
		jobject thiz, jstring userId, jstring token, jstring offset1, jstring limit1){

	char *chname = (*env)->GetStringUTFChars(env, userId, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);
	char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
	char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=announcement.article.getMobile";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&user_id=");
	if(chname != NULL)addString(urlString, chname);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);
	addString(urlString, "&offset1=");
	if(choffset1 != NULL)addString(urlString, choffset1);
	addString(urlString, "&limit1=");
	if(chlimit1 != NULL)addString(urlString, chlimit1);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "announcement.article.getMobile");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4MsgDetails(JNIEnv* env,
		jobject thiz, jstring msgId){

	char *chMsgId = (*env)->GetStringUTFChars(env, msgId, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=announcement.article.get";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chMsgId != NULL)addString(urlString, chMsgId);


	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "announcement.article.get");
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

//�����Ϣδ�Ѷ�״̬ ����pid, userid ,post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4ChangeRead(JNIEnv* env,
		jobject thiz, jstring userid, jstring pid, jstring token){//

	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chpid = (*env)->GetStringUTFChars(env, pid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=ucenter.message.changeRead";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&user_id=");
	if(chuid != NULL)addString(urlString, chuid);

	addString(urlString, "&pid=");
	if(chpid != NULL)addString(urlString, chpid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.message.changeRead");
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

//�����Ϣδ�Ѷ�״̬ ����pid, userid ,post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetReturn(JNIEnv* env,
		jobject thiz, jstring userid, jstring order_code,
		jstring the_date, jstring contact, jstring phone, jstring cause, jstring desc, jstring token){//

	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chorder_code = (*env)->GetStringUTFChars(env, order_code, NULL);
	char *chthe_date = (*env)->GetStringUTFChars(env, the_date, NULL);
	char *chcontact = (*env)->GetStringUTFChars(env, contact, NULL);
	char *chphone = (*env)->GetStringUTFChars(env, phone, NULL);
	char *chcause = (*env)->GetStringUTFChars(env, cause, NULL);
	char *chdesc = (*env)->GetStringUTFChars(env, desc, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=ucenter.returnorder.save";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&user_id=");
	if(chuid != NULL)addString(urlString, chuid);
	addString(urlString, "&order_code=");
	if(chorder_code != NULL)addString(urlString, chorder_code);
	addString(urlString, "&the_date=");
	if(chthe_date != NULL)addString(urlString, chthe_date);
	addString(urlString, "&contact=");
	if(chcontact != NULL)addString(urlString, chcontact);
	addString(urlString, "&contact_manner=");
	if(chphone != NULL)addString(urlString, chphone);
	addString(urlString, "&cause=");
	if(chcause != NULL)addString(urlString, chcause);
	addString(urlString, "&desc=");
	if(chdesc != NULL)addString(urlString, chdesc);
	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.returnorder.save");
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

//查看退换货信息 get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetReturnList(JNIEnv* env,
		jobject thiz, jstring userId,  jstring offset1, jstring limit1, jstring token){//

	char *chname = (*env)->GetStringUTFChars(env, userId, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);
	char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
	char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=ucenter.returnorder.getByUserId";

	addString(urlString, url);
	addString(urlString, api);

//	addString(urlString, "&where=user_id=");
	addString(urlString, "&user_id=");
	if(chname != NULL)addString(urlString, chname);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, "&offset1=");
	if(choffset1 != NULL)addString(urlString, choffset1);
	addString(urlString, "&limit1=");
	if(chlimit1 != NULL)addString(urlString, chlimit1);

//	addString(urlString, "&option%5Boffset%5D=");
//	if(choffset1 != NULL)addString(urlString, choffset1);
//	addString(urlString, "&option%5Blimit%5D=");
//	if(chlimit1 != NULL)addString(urlString, chlimit1);
//
//	addString(urlString, "&option%5Border%5D=the_date+desc&fields=%2A");

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.returnorder.getByUserId");
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

//查看退换货信息 get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetShipLog(JNIEnv* env,
		jobject thiz, jstring code){//,  jstring offset1, jstring limit1, jstring token

	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);
//	char *choffset1 = (*env)->GetStringUTFChars(env, offset1, NULL);
//	char *chlimit1 = (*env)->GetStringUTFChars(env, limit1, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=order.express.shipLog";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

	addString(urlString, pHead);


	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "order.express.shipLog");
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

//获取tn
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetTn(JNIEnv* env,
		jobject thiz, jstring userid, jstring order_code, jstring token){//

	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chorder_code = (*env)->GetStringUTFChars(env, order_code, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=ucenter.order.push";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chuid != NULL)addString(urlString, chuid);
	addString(urlString, "&code=");
	if(chorder_code != NULL)addString(urlString, chorder_code);
	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.push");
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

//get the photo code post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetCode(JNIEnv* env,
		jobject thiz, jstring name){//, jstring order_code, jstring token

	char *chuid = (*env)->GetStringUTFChars(env, name, NULL);
//	char *chorder_code = (*env)->GetStringUTFChars(env, order_code, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=common.code.getCode";

//	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&timelimit=60&count=3&type=num");
	addString(urlString, "&name=");
	if(chuid != NULL)addString(urlString, chuid);

//	if(chorder_code != NULL)addString(urlString, chorder_code);
//	addString(urlString, "&token=");
//	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "common.code.getCode");
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

//send message post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SendMsg(JNIEnv* env,
		jobject thiz, jstring name, jstring code){//, jstring order_code, jstring token

	char *chuid = (*env)->GetStringUTFChars(env, name, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=common.sms.sendCode";

//	addString(urlString, url);
	addString(urlString, api);

//	addString(urlString, "&timelimit=60&count=3&type=num");
	addString(urlString, "&mobileNo=");
	if(chuid != NULL)addString(urlString, chuid);

//	if(chorder_code != NULL)addString(urlString, chorder_code);
	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "common.sms.sendCode");
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

//check the msg code post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4CheckCode(JNIEnv* env,
		jobject thiz, jstring name, jstring code){//, jstring order_code, jstring token

	char *chuid = (*env)->GetStringUTFChars(env, name, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
//	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=common.sms.checkCode";

//	addString(urlString, url);
	addString(urlString, api);

//	addString(urlString, "&timelimit=60&count=3&type=num");
	addString(urlString, "&mobileNo=");
	if(chuid != NULL)addString(urlString, chuid);

//	if(chorder_code != NULL)addString(urlString, chorder_code);
	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "common.sms.checkCode");
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

//check the msg code post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4ResetPsw(JNIEnv* env,
		jobject thiz, jstring name,jstring psw, jstring code){//, jstring order_code, jstring token

	char *chuid = (*env)->GetStringUTFChars(env, name, NULL);
	char *chcode = (*env)->GetStringUTFChars(env, code, NULL);
	char *chpassword = (*env)->GetStringUTFChars(env, psw, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

//	memset(encrypt, 0, LEN * sizeof(char));

	char *api="api=user.passport.resetPassword";

//	addString(urlString, url);
	addString(urlString, api);

//	addString(urlString, "&timelimit=60&count=3&type=num");
	addString(urlString, "&handset=");
	if(chuid != NULL)addString(urlString, chuid);

//	if(chorder_code != NULL)addString(urlString, chorder_code);
	addString(urlString, "&code=");
	if(chcode != NULL)addString(urlString, chcode);
	addString(urlString, "&password=");
	if(chpassword != NULL)addString(urlString, chpassword);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "user.passport.resetPassword");
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

//查看皮肤测试信息 get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SkinQuestion(JNIEnv* env,
		jobject thiz){

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="?api=skin.simple.getQuestion";

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
	addString(encrypt, "skin.simple.getQuestion");
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

//提交皮肤测试数据 post
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SkinAnswer(JNIEnv* env,
		jobject thiz, jstring handset, jstring qq, jstring name,jstring gender, jstring birthday
		, jstring improve_type, jstring skin_type, jstring want_type
		, jstring brand, jstring channel, jstring cpsid, jstring ip){

	char *chhandset = (*env)->GetStringUTFChars(env, handset, NULL);
	char *chqq = (*env)->GetStringUTFChars(env, qq, NULL);
	char *chname = (*env)->GetStringUTFChars(env, name, NULL);
	char *chgender = (*env)->GetStringUTFChars(env, gender, NULL);
	char *chbirthday = (*env)->GetStringUTFChars(env, birthday, NULL);
	char *chimprove_type = (*env)->GetStringUTFChars(env, improve_type, NULL);
	char *chskin_type = (*env)->GetStringUTFChars(env, skin_type, NULL);
	char *chwant_type = (*env)->GetStringUTFChars(env, want_type, NULL);
	char *chbrand = (*env)->GetStringUTFChars(env, brand, NULL);
	char *chchannel = (*env)->GetStringUTFChars(env, channel, NULL);
	char *chcpsid = (*env)->GetStringUTFChars(env, cpsid, NULL);
	char *chip = (*env)->GetStringUTFChars(env, ip, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="api=skin.simple.getAnswer";

	addString(urlString, api);

	addString(urlString, "&handset=");
	if(chhandset != NULL)addString(urlString, chhandset);
	addString(urlString, "&qq=");
	if(chqq != NULL)addString(urlString, chqq);
	addString(urlString, "&name=");
	if(chname != NULL)addString(urlString, chname);
	addString(urlString, "&gender=");
	if(chgender != NULL)addString(urlString, chgender);
	addString(urlString, "&birthday=");
	if(chbirthday != NULL)addString(urlString, chbirthday);
	addString(urlString, "&improve_type=");
	if(chimprove_type != NULL)addString(urlString, chimprove_type);
	addString(urlString, "&skin_type=");
	if(chskin_type != NULL)addString(urlString, chskin_type);
	addString(urlString, "&want_type=");
	if(chwant_type != NULL)addString(urlString, chwant_type);
	addString(urlString, "&brand=");
	if(chbrand != NULL)addString(urlString, chbrand);
	addString(urlString, "&channel=");
	if(chchannel != NULL)addString(urlString, chchannel);
	addString(urlString, "&cpsid=");
	if(chcpsid != NULL)addString(urlString, chcpsid);
	addString(urlString, "&ip=");
	if(chip != NULL)addString(urlString, chip);



	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "skin.simple.getAnswer");
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

//查看皮肤测试信息 get
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4CheckCps(JNIEnv* env,
		jobject thiz, jstring id){

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *chid = (*env)->GetStringUTFChars(env, id, NULL);

	char *api="?api=user.info.checkCpsId";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&id=");
	if(chid != NULL)addString(urlString, chid);

	addString(urlString, pHead);



	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "user.info.checkCpsId");
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

//获取tn
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SignUp(JNIEnv* env,
		jobject thiz, jstring userid, jstring token){//

	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="api=ucenter.gold.add";
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	if(chuid != NULL)addString(urlString, chuid);
	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.gold.add");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4SignCount(JNIEnv* env,
		jobject thiz, jstring userid, jstring token){//

	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="api=ucenter.gold.singCount";
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	if(chuid != NULL)addString(urlString, chuid);
	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.gold.singCount");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4AlicSign(JNIEnv* env,
		jobject thiz, jstring userid, jstring token, jstring ordercode){//

	char *chuid = (*env)->GetStringUTFChars(env, userid, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);
	char *chorder = (*env)->GetStringUTFChars(env, ordercode, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="api=ucenter.order.alicSign";
	addString(urlString, api);

	addString(urlString, "&user_id=");
	if(chuid != NULL)addString(urlString, chuid);
	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);
	addString(urlString, "&code=");
	if(chorder != NULL)addString(urlString, chorder);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.order.alicSign");
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

jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetGold(JNIEnv* env,
		jobject thiz, jstring id, jstring token){

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *chid = (*env)->GetStringUTFChars(env, id, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char *api="?api=ucenter.gold.get";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&customer_id=");
	if(chid != NULL)addString(urlString, chid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);



	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ucenter.gold.get");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetTicket(JNIEnv* env,
		jobject thiz, jstring id, jstring token){

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *chid = (*env)->GetStringUTFChars(env, id, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char *api="?api=ticket.usertickets.getByUserId";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&userid=");
	if(chid != NULL)addString(urlString, chid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "ticket.usertickets.getByUserId");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetShark(JNIEnv* env,
		jobject thiz, jstring id, jstring token){

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *chid = (*env)->GetStringUTFChars(env, id, NULL);
	char *chtoken = (*env)->GetStringUTFChars(env, token, NULL);

	char *api="?api=active.shake.save";

	addString(urlString, url);
	addString(urlString, api);

	addString(urlString, "&user_id=");
	if(chid != NULL)addString(urlString, chid);

	addString(urlString, "&token=");
	if(chtoken != NULL)addString(urlString, chtoken);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "active.shake.save");
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
jstring __attribute__ ((visibility ("default"))) Java_com_yidejia_app_mall_jni_JNICallBack_getHttp4GetYiRiHui(JNIEnv* env,
		jobject thiz, jstring type, jstring offset, jstring limit, jstring sort){//

	char *chtype = (*env)->GetStringUTFChars(env, type, NULL);
	char *choffset = (*env)->GetStringUTFChars(env, offset, NULL);
	char *chlimit = (*env)->GetStringUTFChars(env, limit, NULL);
	char *chsort = (*env)->GetStringUTFChars(env, sort, NULL);

	char encrypt[LEN] , urlString[LEN];
	encrypt[0] = 0;
	urlString[0] = 0;

	char *api="api=active.yirihui.getYrhList";
	addString(urlString, api);

	addString(urlString, "&type=");
	if(chtype != NULL)addString(urlString, chtype);
	addString(urlString, "&offset=");
	if(choffset != NULL)addString(urlString, choffset);
	addString(urlString, "&limit=");
	if(chlimit != NULL)addString(urlString, chlimit);
	addString(urlString, "&sort=");
	if(chsort != NULL)addString(urlString, chsort);

	addString(urlString, pHead);

	time_t currtime = time(NULL);
	long ltime = currtime;
	char chtime[20];

	sprintf(chtime, "%ld", ltime);
	addString(urlString, chtime);
	addString(urlString, "&sign=");
	addString(encrypt, strTemp);
	addString(encrypt, "active.yirihui.getYrhList");
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
