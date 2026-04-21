<template>
	<view class="container">
		<button type="primary" @click="handleGetList">获取文章列表</button>

		<view class="article-item" v-for="item in articleList" :key="item.id">
			<text class="title">{{ item.title }}</text>
			<text class="content">{{ item.content }}</text>
		</view>
	</view>
</template>

<script setup>
	import {
		ref
	} from 'vue'

	const articleList = ref([])

	const handleGetList = async () => {
		// 🔥 关键：只读取本地存储的最新Token，删除硬编码旧Token！
		const token = uni.getStorageSync('TOKEN')

		// 如果没有Token，直接跳去登录，不发请求
		if (!token) {
			uni.showToast({
				title: '请先登录',
				icon: 'none'
			})
			uni.navigateTo({
				url: '/pages/login/login'
			}) // 跳登录页
			return
		}

		uni.showLoading({
			title: '加载中...'
		})

		try {
			const res = await uni.request({
				url: 'http://localhost:8080/articles/list',
				// url: 'http://localhost:8080/user/userInfo',//用户信息测试POST
				method: 'GET',
				header: {
					"Authorization": token // 最新的有效Token
				}
			})
			console.log(res)

			if (res.statusCode === 200 && res.data.code === 200) {
				articleList.value = res.data.data
				uni.showToast({
					title: '获取成功',
					icon: 'success'
				})
			} else {
				// 401=Token过期，自动清除旧token，跳登录
				if (res.statusCode === 401) {
					uni.removeStorageSync('TOKEN')
					uni.showToast({
						title: '登录已过期，请重新登录',
						icon: 'none'
					})
					uni.navigateTo({
						url: '/pages/login/login'
					})
					return
				}
				uni.showToast({
					title: res.data.message || '请求失败',
					icon: 'none'
				})
			}
		} catch (err) {
			console.error('请求异常', err)
			uni.showToast({
				title: '网络异常',
				icon: 'none'
			})
		} finally {
			uni.hideLoading()
		}
	}
</script>

<style scoped>
	.container {
		padding: 20rpx;
	}

	.article-item {
		margin: 20rpx 0;
		padding: 20rpx;
		border: 1rpx solid #eee;
		border-radius: 10rpx;
	}

	.title {
		font-size: 32rpx;
		font-weight: bold;
		display: block;
		margin-bottom: 10rpx;
	}

	.content {
		font-size: 28rpx;
		color: #666;
	}
</style>