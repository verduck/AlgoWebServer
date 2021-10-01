import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const initialState = {
  isLoggedin: false,
  token: ''
};

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    authenticationSuccess: (state, action) => {
      state.isLoggedin = true;
      state.token = action.payload;
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + state.token;
      sessionStorage.setItem('token', state.token);
    }
  }
});

export const { authenticationSuccess } = authSlice.actions;

export const selectAuth = (state) => state.auth;

export default authSlice.reducer;