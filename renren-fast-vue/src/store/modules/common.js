export default {
  namespaced: true,
  state: {
    // 頁面文檔可視高度(隨窗口改變大小)
    documentClientHeight: 0,
    // 導航條, 佈局風格, defalut(默認) / inverse(反向)
    navbarLayoutType: 'default',
    // 側邊欄, 佈局皮膚, light(淺色) / dark(黑色)
    sidebarLayoutSkin: 'dark',
    // 側邊欄, 摺疊狀態
    sidebarFold: false,
    // 側邊欄, 菜單
    menuList: [],
    menuActiveName: '',
    // 內容, 是否需要刷新
    contentIsNeedRefresh: false,
    // 主入口標籤頁
    mainTabs: [],
    mainTabsActiveName: ''
  },
  mutations: {
    updateDocumentClientHeight (state, height) {
      state.documentClientHeight = height
    },
    updateNavbarLayoutType (state, type) {
      state.navbarLayoutType = type
    },
    updateSidebarLayoutSkin (state, skin) {
      state.sidebarLayoutSkin = skin
    },
    updateSidebarFold (state, fold) {
      state.sidebarFold = fold
    },
    updateMenuList (state, list) {
      state.menuList = list
    },
    updateMenuActiveName (state, name) {
      state.menuActiveName = name
    },
    updateContentIsNeedRefresh (state, status) {
      state.contentIsNeedRefresh = status
    },
    updateMainTabs (state, tabs) {
      state.mainTabs = tabs
    },
    updateMainTabsActiveName (state, name) {
      state.mainTabsActiveName = name
    }
  }
}
