import { createSlice } from '@reduxjs/toolkit';

export const alertType = {
  success: 'success',
  error: 'error',
  warning: 'warning',
  info: 'info'
}

const initialState = {
  open: false,
  type: alertType.success,
  message: ''
}

export const alertSlice = createSlice({
  name: 'alert',
  initialState,
  reducers: {
    openAlert: (state, action) => {
      state.open = true;
      state.type = action.payload.type;
      state.message = action.payload.message;
    },

    closeAlert: (state) => {
      state.open = false;
    }
  }
});

export const { openAlert, closeAlert } = alertSlice.actions;

export default alertSlice.reducer;