import React from 'react';
import { useSelector } from 'react-redux';
import { Redirect, Route } from 'react-router-dom';
import { selectAuth } from '../reducers/AuthReducer';

function PrivateRoute ({ component: Component, ...rest }) {
  const auth = useSelector(selectAuth);

  return (
    <Route
      {...rest}
      render = {props => 
        auth.isLoggendin ? (
          <Component {...props} />
        ) : ( 
          <Redirect to={{
            pathname: '/', 
            state: {from: props.location}
          }} />
        )
      }
    />
  )
}

export default PrivateRoute;