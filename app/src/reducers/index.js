import { combineReducers } from '@reduxjs/toolkit';

import AlertReducer from './AlertReducer';
import AuthReducer from './AuthReducer';
import UserReducer from './UserReducer';

const rootReducer = combineReducers({
  alert: AlertReducer,
  auth: AuthReducer,
  user: UserReducer
});

export default rootReducer;