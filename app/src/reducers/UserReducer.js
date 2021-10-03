import { createSlice } from "@reduxjs/toolkit";

const initialState = {
	studentId: '',
	name: ''
};

export const userSlice = createSlice({
	name: 'user',
	initialState,
	reducers: {
		setName: (state, action) => {
			state.name = action.payload;
		}
	}
});

export default userSlice.reducer;