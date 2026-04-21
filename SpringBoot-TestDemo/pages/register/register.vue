<template>
	<!-- uni-app 标准布局：view 替代 div -->
	<view class="register-container">
		<view class="register-box">
			<view class="title">用户注册</view>

			<!-- 用户名输入框 -->
			<view class="form-item">
				<input v-model="form.username" class="input" type="text" placeholder="请输入用户名" />
			</view>

			<!-- 密码输入框 -->
			<view class="form-item">
				<input v-model="form.password" class="input" type="password" placeholder="请输入密码" />
			</view>

			<!-- 确认密码 -->
			<view class="form-item">
				<input v-model="form.confirmPwd" class="input" type="password" placeholder="请确认密码" />
			</view>

			<!-- 注册按钮 -->
			<button class="register-btn" @click="handleRegister">立即注册</button>
		</view>
	</view>
</template>

<script setup>
	import {
		ref
	} from 'vue'

	// 表单数据
	const form = ref({
		username: '',
		password: '',
		confirmPwd: ''
	})

	// 注册提交方法（uni-app 原生请求）
	const handleRegister = () => {
		// 前端基础校验
		if (!form.value.username || !form.value.password) {
			uni.showToast({
				title: '用户名/密码不能为空',
				icon: 'none'
			})
			return
		}
		if (form.value.password !== form.value.confirmPwd) {
			uni.showToast({
				title: '两次密码不一致',
				icon: 'none'
			})
			return
		}

		// uni-app 原生网络请求（替代 axios）
		uni.request({
			url: 'http://localhost:8080/user/register', // 后端接口地址
			method: 'POST',
			data: {
				username: form.value.username,
				password: form.value.password
			},
			// 请求成功回调
			success: (res) => {
				const data = res.data
				if (data.code === 200) {
					uni.showToast({
						title: '注册成功！',
						icon: 'success'
					})
					// 清空表单
					form.value = {
						username: '',
						password: '',
						confirmPwd: ''
					}
					console.log(res);
				} else {
					console.log(res);
					uni.showToast({
						title: data.msg || '注册失败',
						icon: 'none'
					})
				}
			},
			// 请求失败回调
			fail: () => {
				uni.showToast({
					title: '网络错误，请检查后端服务',
					icon: 'none'
				})
			}
		})
	}
</script>

<style scoped>
	/* uni-app 标准样式 */
	.register-container {
		width: 100vw;
		height: 100vh;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #f5f5f5;
	}

	.register-box {
		width: 80%;
		padding: 30rpx;
		background: #fff;
		border-radius: 16rpx;
		box-shadow: 0 0 10rpx rgba(0, 0, 0, 0.1);
	}

	.title {
		font-size: 36rpx;
		font-weight: bold;
		text-align: center;
		margin-bottom: 40rpx;
		color: #333;
	}

	.form-item {
		margin-bottom: 30rpx;
	}

	.input {
		width: 100%;
		height: 80rpx;
		border: 1rpx solid #eee;
		border-radius: 8rpx;
		padding: 0 20rpx;
		font-size: 28rpx;
		box-sizing: border-box;
	}

	.register-btn {
		width: 100%;
		height: 80rpx;
		background-color: #007aff;
		color: #fff;
		border-radius: 8rpx;
		font-size: 30rpx;
		margin-top: 20rpx;
	}
</style>