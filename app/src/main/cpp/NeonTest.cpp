//
// Created by xjh on 17-9-26.
//
#include <jni.h>
#include <cwchar>
#include <vector>
#include <arm_neon.h>

#ifndef _Included_pers_xjh_note_model_bean_NeonTest
#define _Included_pers_xjh_note_model_bean_NeonTest

using namespace std;

#ifdef __cplusplus
extern "C" {
#endif

//正常的数组相乘
JNIEXPORT void JNICALL Java_pers_xjh_note_model_bean_NeonTest_normalArrayMul(JNIEnv *env, jclass type,
                     jfloatArray array_a, jfloatArray array_b, jfloatArray array_result) {
    int size = env->GetArrayLength(array_a);
    jfloat* a = env->GetFloatArrayElements(array_a, JNI_FALSE);
    jfloat* b = env->GetFloatArrayElements(array_b, JNI_FALSE);
    jfloat* c = env->GetFloatArrayElements(array_result, JNI_FALSE);

    for (size_t i = 0; i < size; i++) {
        c[i] = a[i] * b[i];
    }
}

//NEON优化的数组相乘
JNIEXPORT void JNICALL Java_pers_xjh_note_model_bean_NeonTest_neonArrayMul(JNIEnv *env,  jclass type,
                       jfloatArray array_a, jfloatArray array_b, jfloatArray array_result) {
    int size = env->GetArrayLength(array_a);
    jfloat* a = env->GetFloatArrayElements(array_a, JNI_FALSE);
    jfloat* b = env->GetFloatArrayElements(array_b, JNI_FALSE);
    jfloat* c = env->GetFloatArrayElements(array_result, JNI_FALSE);

    int i = 0;
    for (; i < size - 3; i += 4) {
        const auto data_a = vld1q_f32(&a[i]);
        const auto data_b = vld1q_f32(&b[i]);
        const auto data_c = vmulq_f32(data_a, data_b);
        vst1q_f32(c+i, data_c);
    }

    for (; i < size; i++) {
        c[i] = a[i] * b[i];
    }
}

//正常的vector相乘 （注意：需要关闭编译器的自动向量化优化）
JNIEXPORT void JNICALL Java_pers_xjh_note_model_bean_NeonTest_normalVectorMul(JNIEnv *env,  jclass type, jint size) {

    std::vector<float> vec_a(size);
    std::vector<float> vec_b(size);
    std::vector<float> vec_result(size);

    for (size_t i = 0; i < vec_result.size();i++) {
        vec_result[i] = vec_a[i] * vec_b[i];
    }
}

//NEON优化的vector相乘
JNIEXPORT void JNICALL Java_pers_xjh_note_model_bean_NeonTest_neonVectorMul(JNIEnv *env,  jclass type, jint size) {

    std::vector<float> vec_a(size);
    std::vector<float> vec_b(size);
    std::vector<float> vec_result(size);

    int i = 0;
    for (; i < (int)vec_result.size() - 3; i+=4) {
        const auto data_a = vld1q_f32(&vec_a[i]);
        const auto data_b = vld1q_f32(&vec_b[i]);
        float* dst_ptr = &vec_result[i];
        const auto data_res = vmulq_f32(data_a, data_b);
        vst1q_f32(dst_ptr, data_res);
    }

    for (; i < (int)vec_result.size(); i++) {
        vec_result[i] = vec_a[i] * vec_b[i];
    }
}

#ifdef __cplusplus
}
#endif
#endif