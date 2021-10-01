import { combineReducers } from '@reduxjs/toolkit';

import AlertReducer from './AlertReducer';
import AuthReducer from './AuthReducer';

const rootReducer = combineReducers({
  alert: AlertReducer,
  auth: AuthReducer
});

export default rootReducer;