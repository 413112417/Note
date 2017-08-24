/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <string>
#include <assert.h>
/* Header for class pers_xjh_note_model_bean_JNITest */

#ifndef _Included_pers_xjh_note_model_bean_JNITest
#define _Included_pers_xjh_note_model_bean_JNITest

using namespace std;
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL native_hello(JNIEnv *env, jclass clazz);
static int registerNatives(JNIEnv* env);

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env = NULL;
    jint result = -1;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    assert(env != NULL);

    if (!registerNatives(env)) {//注册
        return -1;
    }
    /* success -- return valid version number */
    result = JNI_VERSION_1_4;

    return result;
}

#define JNIREG_CLASS "pers/xjh/note/model/bean/JNITest"//指定要注册的类

/**
 * Table of methods associated with a single class.
 */
static JNINativeMethod gMethods[] = {
        { "hello", "()Ljava/lang/String;", (void*) native_hello },//绑定
};

JNIEXPORT jstring JNICALL native_hello(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("onload注册函数");
}

/**
 * Register several native methods for one class.
 */
static int registerNativeMethods(JNIEnv* env, const char* className, JNINativeMethod* gMethods, int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

/**
 * Register native methods for all classes we know about.
 */
static int registerNatives(JNIEnv* env) {
    if (!registerNativeMethods(env, JNIREG_CLASS, gMethods, sizeof(gMethods) / sizeof(gMethods[0])))
        return JNI_FALSE;
    return JNI_TRUE;
}

/**
 * Class:     pers_xjh_note_model_bean_JNITest
 * Method:    sayHello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_pers_xjh_note_model_bean_JNITest_sayHello(JNIEnv *env, jclass type) {
    //你可以通过JNI函数NewStringUTF在本地方法中创建一个新的java.lang.String字符串对象。
    return env->NewStringUTF("Hello from C++");
}

JNIEXPORT jint JNICALL Java_pers_xjh_note_model_bean_JNITest_add(JNIEnv *env, jclass type, jint a, jint b) {
    //JNI对基本类型和引用类型的处理是不同的。基本类型的映射是一对一的。例如JAVA中的int类型直接对应C/C++中的jint
    return a + b;
}

JNIEXPORT jstring JNICALL Java_pers_xjh_note_model_bean_JNITest_changeString(JNIEnv *env, jclass type, jstring s_) {
    const char *s = env->GetStringUTFChars(s_, 0);

    if (s == NULL) {
        //不要忘记检查GetStringUTFChars。因为JVM需要为新诞生的UTF-8字符串分配内存，这个操作有可能因为内存太少而失败。
        return NULL;
    }
    string a = s;
    a.append("from c++");
    env->ReleaseStringUTFChars(s_, s);
    return env->NewStringUTF(a.c_str());
}

JNIEXPORT jint JNICALL Java_pers_xjh_note_model_bean_JNITest_sumArray(JNIEnv *env, jclass type, jintArray array_) {
    jint *array = env->GetIntArrayElements(array_, NULL);

    int sum = 0;
    int size = env->GetArrayLength(array_);
    for(int i=0; i< size; i++) {
        sum += array[i];
    }

    env->ReleaseIntArrayElements(array_, array, 0);

    return sum;
}

JNIEXPORT jstring JNICALL Java_pers_xjh_note_model_bean_JNITest_objectArray(JNIEnv *env, jclass type, jobjectArray array) {
    string str = "";
    int size = env->GetArrayLength(array);
    for(int i=0; i< size; i++) {
        jobject object = env->GetObjectArrayElement(array, i);

        jclass cls = env->GetObjectClass(object);
        jmethodID mid = env->GetMethodID(cls, "getNoteName", "()Ljava/lang/String;");

        jstring s = (jstring) env->CallObjectMethod(object, mid);
        str.append(env->GetStringUTFChars(s, 0));
    }

    return env->NewStringUTF(str.c_str());
}

JNIEXPORT jstring JNICALL Java_pers_xjh_note_model_bean_JNITest_getNoteName(JNIEnv *env, jclass type, jobject note) {
    jclass cls = env->GetObjectClass(note);
    jmethodID mid = env->GetMethodID(cls, "getNoteName", "()Ljava/lang/String;");

    if (mid == NULL) {
        return 0;
    }
    return (jstring) env->CallObjectMethod(note, mid);
}

JNIEXPORT void JNICALL Java_pers_xjh_note_model_bean_JNITest_setNoteName(JNIEnv *env, jclass type, jobject note) {
    jclass cls = env->GetObjectClass(note);
    jmethodID mid = env->GetMethodID(cls, "setNoteName", "(Ljava/lang/String;)V");
    //TODO 为什么"...."不行？
    env->CallVoidMethod(note, mid, env->NewStringUTF("I'm from C!"));
}

JNIEXPORT void JNICALL Java_pers_xjh_note_model_bean_JNITest_staticFieldAccess(JNIEnv *env, jclass type) {
    //访问静态字段
    jfieldID fid = env->GetStaticFieldID(type, "staticField", "I");
    if (fid == NULL) {
        return;
    }
    env->SetStaticIntField(type, fid, 6);
}

#ifdef __cplusplus
}
#endif
#endif