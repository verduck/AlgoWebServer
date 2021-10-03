import React from 'react';
import { Redirect, Route, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { openAlert, alertType } from '../reducers/AlertReducer';

class PrivateRoute extends React.Component {
  constructor(props) {
    super(props);
    this.showMessage = this.showMessage.bind(this);
  }

  componentDidMount() {
    const { auth } = this.props;

    if (!auth.isLoggedin) {
      this.showMessage();
    }
  }

  showMessage() {
    const paylaod = {
      type: alertType.error,
      message: '로그인이 필요한 서비스입니다.'
    };
    this.props.openAlert(paylaod);
  }

  render() {
    const { component: Component, auth, ...rest } = this.props;
    return (
      <Route
        {...rest}
        render = {props => (
          auth.isLoggedin ? (
            <Component {...props} />
          ) : (
            <Redirect to={{
              pathname: '/',
              state: {from: props.location}
            }} />
          )
        )}
      />
    )
  }
}

const mapStateToProps = (state) => ({
  auth: state.auth
});

const mapDispatchToProps = { openAlert };

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(PrivateRoute));