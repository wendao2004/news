<script setup>
	// 全局监听页面跳转，校验登录状态
	uni.addInterceptor('navigateTo', {
		invoke(e) {
			checkLogin(e.url)
		}
	})
	uni.addInterceptor('redirectTo', {
		invoke(e) {
			checkLogin(e.url)
		}
	})
	uni.addInterceptor('switchTab', {
		invoke(e) {
			checkLogin(e.url)
		}
	})

	// 登录校验核心逻辑
	const checkLogin = (url) => {
		const token = uni.getStorageSync('TOKEN')
		// 白名单：不需要登录的页面
		const whiteList = ['/pages/login/login', '/pages/register/register']

		// 1. 已登录 + 访问登录/注册页 → 强制跳我的页面
		if (token && whiteList.includes(url)) {
			uni.showToast({
				title: '您已登录',
				icon: 'none'
			})
			// 跳转到我的页面（修改为你的tab页路径）
			uni.switchTab({
				url: '/pages/me/me'
			})
			return false
		}

		// 2. 未登录 + 访问需要权限的页面(我的/文章) → 跳登录
		if (!token && !whiteList.includes(url)) {
			uni.showToast({
				title: '请先登录',
				icon: 'none'
			})
			uni.redirectTo({
				url: '/pages/login/login'
			})
			return false
		}
	}
</script>

<style>
	/* 全局样式 */
	page {
		background-color: #f5f5f5;
	}
</style>