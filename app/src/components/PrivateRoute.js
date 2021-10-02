import React from 'react';
import { connect } from 'react-redux';
import { Redirect, Route, withRouter } from 'react-router-dom';

function PrivateRoute ({ component: Component, auth, ...rest }) {
  console.log(auth)
  return (
    <Route
      {...rest}
      render = {props => 
        auth.isLoggedin ? (
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

const mapState = (state) => ({
  auth: state.auth
});

export default withRouter(connect(mapState)(PrivateRoute));