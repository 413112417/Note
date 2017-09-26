//
// Created by xjh on 17-9-26.
//


#include <jni.h>
#include <assert.h>
#include <cwchar>

using namespace std;

#ifdef __cplusplus
extern "C" {
#endif

//正常的vector相乘 （注意：需要关闭编译器的自动向量化优化）
JNIEXPORT static void JNICALL normal_vector_mul(JNIEnv *env, jint a) {

}

#ifdef __cplusplus
}
#endif
#endif