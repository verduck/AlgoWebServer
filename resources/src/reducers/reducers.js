import { combineReducers } from 'redux';

import UserReducer from './AuthReducer';

const rootReducer = combineReducers({
  user: UserReducer,
});

export default rootReducer;