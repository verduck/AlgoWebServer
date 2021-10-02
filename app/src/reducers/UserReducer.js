import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    studentId: '123',
    name: '홍길동',
    color: '',
};

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {

    }
});

export default userSlice.reducer;