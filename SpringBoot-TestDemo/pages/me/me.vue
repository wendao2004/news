<template>
	<view class="user-container">
		<view class="user-card">
			<view class="avatar-box">
				<image class="avatar" src="/static/default-avatar.png"></image>
			</view>
			<view class="info">
				<view class="nickname">{{ userInfo.nickname || '暂无昵称' }}</view>
				<view class="username">账号：{{ userInfo.username || '未知' }}</view>
			</view>
		</view>

		<view class="detail-list">
			<view class="item">
				<text class="label">用户ID</text>
				<text class="value">{{ userInfo.id || '-' }}</text>
			</view>
			<view class="item">
				<text class="label">邮箱</text>
				<text class="value">{{ userInfo.email || '未设置' }}</text>
			</view>
			<view class="item">
				<text class="label">创建时间</text>
				<text class="value">{{ userInfo.createTime || '暂无' }}</text>
			</view>
			<view class="item">
				<text class="label">更新时间</text>
				<text class="value">{{ userInfo.updateTime || '暂无' }}</text>
			</view>
		</view>

		<button class="logout-btn" @click="logout">退出登录</button>
	</view>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue'

	// 直接读取本地存储的用户信息，完美匹配后端字段
	const userInfo = ref(uni.getStorageSync('USER_INFO') || {})

	// 页面加载刷新数据
	onMounted(() => {
		userInfo.value = uni.getStorageSync('USER_INFO')
	})

	// 退出登录：销毁 token + 用户信息
	const logout = () => {
		uni.showModal({
			title: '提示',
			content: '确定退出登录吗？',
			success: (res) => {
				if (res.confirm) {
					// 清空所有登录缓存
					uni.removeStorageSync('TOKEN')
					uni.removeStorageSync('USER_INFO')
					uni.showToast({
						title: '退出成功'
					})
					uni.redirectTo({
						url: '/pages/login/login'
					})
				}
			}
		})
	}
</script>

<style scoped>
	.user-container {
		padding: 30rpx;
		background: #f7f8fa;
		min-height: 100vh;
	}

	.user-card {
		background: #fff;
		padding: 40rpx;
		border-radius: 20rpx;
		display: flex;
		align-items: center;
		margin-bottom: 30rpx;
	}

	.avatar {
		width: 120rpx;
		height: 120rpx;
		border-radius: 50%;
		background: #eee;
	}

	.info {
		margin-left: 30rpx;
	}

	.nickname {
		font-size: 40rpx;
		font-weight: bold;
	}

	.username {
		font-size: 28rpx;
		color: #666;
		margin-top: 10rpx;
	}

	.detail-list {
		background: #fff;
		border-radius: 20rpx;
		padding: 0 30rpx;
		margin-bottom: 50rpx;
	}

	.item {
		display: flex;
		justify-content: space-between;
		padding: 30rpx 0;
		border-bottom: 1rpx solid #eee;
	}

	.item:last-child {
		border-bottom: none;
	}

	.label {
		font-size: 30rpx;
		color: #666;
	}

	.value {
		font-size: 30rpx;
		color: #333;
	}

	.logout-btn {
		background: #ff4444;
		color: #fff;
		border-radius: 10rpx;
		height: 80rpx;
	}
</style>