import { userInfo } from "os";
import { createStore } from "vuex";

// 定义用户信息类型
interface UserInfo {
  username: string;
  token?: string;
  loginTime?: number;
}

// 定义 State 类型
interface State {
  userInfo: UserInfo | null;
}

export default createStore({
  state: {
    userInfo: null as UserInfo | null,
  },

  mutations: {
    SET_USER_INFO(state: State, data: UserInfo) {
      state.userInfo = data;
      localStorage.setItem("userInfo", JSON.stringify(data));
    },
    CLEAR_USER_INFO(state: State) {
      state.userInfo = null;
      localStorage.removeItem("userInfo");
    },
  },

  actions: {
    loginSuccess(
      { commit }: { commit: (type: string, data: UserInfo) => void },
      userInfo: UserInfo
    ) {
       const data = {
      ...userInfo,
      loginTime: Date.now(),
    };
      commit("SET_USER_INFO", data);
    },
    logout({ commit }: { commit: (type: string) => void }) {
      commit("CLEAR_USER_INFO");
    },
    initUser({ commit }: { commit: (type: string, data?: UserInfo) => void }) {
    const info = localStorage.getItem("userInfo");
    if (info) {
      commit("SET_USER_INFO", JSON.parse(info));
    }
  },
},

  getters: {
    getUserInfo: (state: State) => state.userInfo,
    isLogin: (state: State) => {
      const user = state.userInfo;
      if (!user || !user.token || !user.loginTime) return false;
      return Date.now() - user.loginTime < 3600000; // 1小时有效期
    },
  },
});