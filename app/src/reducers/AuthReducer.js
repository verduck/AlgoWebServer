import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const initialState = {
  isLoggedin: false,
  error: false,
  message: '',
  token: ''
};

export const authenticate = createAsyncThunk("auth/authenticate", async (data, { rejectWithValue }) => {
  try {
    const res = await axios.post('/api/v1/auth/authenticate', data)
    return res.data
  } catch (err) {
    let error = err
    if (!error.response) {
      throw err
    }

    return rejectWithValue(error.response.data)
  }
});

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    authenticationSuccess: (state, action) => {
      state.isLoggedin = true
      state.token = action.payload
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + state.token
      sessionStorage.setItem('token', state.token)
    },
    resetError: (state) => {
      state.error = false
      state.message = ''
    }
  },
  extraReducers: {
    [authenticate.fulfilled]: (state, action) => {
      const data = action.payload
      state.isLoggedin = true;
      state.token = data.token
      if (state.authority === 'ROLE_APPLICANT') {
        state.message = "등록되지 않은 사용자입니다.\n알고리즘 랩장에게 문의하세요."
      }
    },
    [authenticate.rejected]: (state, action) => {
      const data = action.payload
      state.isLoggedin = data.result
      state.error = true
      state.message = "학번 또는 비밀번호가 잘못되었습니다."
    }
  }
});

export const { authenticationSuccess, resetError } = authSlice.actions

export const selectAuth = (state) => state.auth

export default authSlice.reducer