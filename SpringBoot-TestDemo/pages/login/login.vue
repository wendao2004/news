<template>
	<view class="login-container">
		<view class="login-box">
			<view class="title">用户登录</view>
			<view class="form-item">
				<input v-model="form.username" class="input" type="text" placeholder="请输入用户名" />
			</view>
			<view class="form-item">
				<input v-model="form.password" class="input" type="password" placeholder="请输入密码" />
			</view>
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
import { ref, onMounted } from 'vue'

const form = ref({
	username: '',
	password: '',
})

// 页面加载检测登录状态：已登录直接跳转文章页
onMounted(() => {
	const token = uni.getStorageSync('TOKEN')
	if (token) {
		uni.switchTab({ url: "/pages/article/article" })
	}
})

const handleLogin = async () => {
	if (!form.value.username || !form.value.password) {
		uni.showToast({ title: '用户名/密码不能为空', icon: 'none' })
		return
	}

	uni.request({
		url: 'http://localhost:8080/user/login',
		method: 'POST',
		data: {
			username: form.value.username,
			password: form.value.password
		},
		success: async (res) => {
			const result = res.data
			if (result.code === 200) {
				// 保存登录返回的token
				const token = result.data
				uni.setStorageSync('TOKEN', token)

				// 按照你后端格式 请求用户信息接口
				const userRes = await uni.request({
					url: "http://localhost:8080/user/userInfo",
					method: 'POST',
					header: {
						Authorization: token
					}
				})

				// 【严格匹配你的返回格式】用户信息在 userRes.data.data
				const userInfo = userRes.data.data
				// 直接存储对象，uni-app原生支持，无需json转义
				uni.setStorageSync('USER_INFO', userInfo)

				uni.showToast({ title: '登录成功！', icon: 'success' })
				// 清空表单
				form.value = { username: '', password: '' }
				// 跳转文章页
				uni.switchTab({ url: "/pages/article/article" })

			} else {
				uni.showToast({ title: result.message || '登录失败', icon: 'none' })
			}
		},
		fail: () => {
			uni.showToast({ title: '网络错误，请检查后端服务', icon: 'none' })
		}
	})
}

const gotoPage = () => {
	uni.navigateTo({ url: "/pages/register/register" })
}
</script>

<style scoped>
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