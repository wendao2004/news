<template>
	<view class="login-container">
		<view class="login-box">
			<view class="title">用户登录</view>
			<!-- 用户名输入框 -->
			<view class="form-item">
				<input v-model="form.username" class="input" type="text" placeholder="请输入用户名" />
			</view>

			<!-- 密码输入框 -->
			<view class="form-item">
				<input v-model="form.password" class="input" type="password" placeholder="请输入密码" />
			</view>

			<!-- 登录按钮 -->
			<button class="register-btn" @click="handleLogin">立即登录</button>
		</view>
		<view class="register-text">
			<view style="display: flex;flex-direction: row;">
				<text>没有账号？</text>
				<text style="color:#007aff ;" @click="gotoPage">点击注册</text>
			</view>
		</view>
	</view>

</template>

<script setup>
	import { ref } from 'vue'

	// 表单数据
	const form = ref({
		username: '',
		password: '',
	})

	// 登录提交方法
	const handleLogin = () => {
		console.log("点击登录");
		// 前端基础校验
		if (!form.value.username || !form.value.password) {
			uni.showToast({
				title: '用户名/密码不能为空',
				icon: 'none'
			})
			return
		}

		// 发送登录请求
		uni.request({
			url: 'http://localhost:8080/user/login',
			method: 'POST',
			data: {
				username: form.value.username,
				password: form.value.password
			},
			success: (res) => {
				const data = res.data
				if (data.code === 200) {
					// ===================== 核心修复：登录成功 保存新Token =====================
					uni.setStorageSync('TOKEN', data.data); 
					
					uni.showToast({
						title: '登录成功！',
						icon: 'success'
					})
					// 清空表单
					form.value = {
						username: '',
						password: ''
					}
					// 跳转到文章页
					uni.redirectTo({
						url: "/pages/ariticle/ariticle"
					})
				} else {
					uni.showToast({
						title: data.message || '登录失败',
						icon: 'none'
					})
				}
			},
			fail: () => {
				uni.showToast({
					title: '网络错误，请检查后端服务',
					icon: 'none'
				})
			}
		})
	}

	const gotoPage = () => {
		console.log("点击注册")
		uni.navigateTo({
			url: "/pages/register/register"
		})
	}
</script>

<style scoped>
	/* uni-app 标准样式 */
	.login-container {
		flex-direction: column;
		width: 100vw;
		height: 100vh;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #f5f5f5;
	}

	.login-box {
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

	.register-text {
		margin: 2%;
	}
</style>