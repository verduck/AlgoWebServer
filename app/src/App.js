import React from 'react';
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import { styled, alpha } from '@mui/material/styles';
import { AppBar, Toolbar, IconButton, Breadcrumbs, Box, Button, Snackbar, Alert } from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';

import PrivateRoute from './components/PrivateRoute';
import SearchBar from './components/SearchBar';
import LoginDialog from './components/LoginDialog';
import UserAvatar from './components/UserAvatar';

import Home from './views/Home';
import Landing from './views/Landing';
import Board from './views/Board';
import Write from './views/Write';

import { connect } from 'react-redux';
import { authenticationSuccess } from './reducers/AuthReducer';
import { openAlert, closeAlert } from './reducers/AlertReducer';

const BluredAppBar = styled(AppBar)(({ theme }) => ({
  backdropFilter: 'blur(10px)',
  boxShadow: 'inset 0px -1px 1px #eaeef3',
  backgroundColor: alpha("#ffffff", 0.72)
}));

const NavLink = styled(Link)(({ theme }) => ({
  textDecoration: 'inherit',
  
  '&:visited': {
    color: theme.palette.primary.main,
    textDecoration: 'inherit'
  },

  '&:hover': {
    color: theme.palette.primary.dark,
    textDecoration: 'inherit'
  }
}));

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state ={
      open: false
    };

    this.handleClickOpen = this.handleClickOpen.bind(this);
    this.handleClose = this.handleClose.bind(this);
    this.handleCloseAlert = this.handleCloseAlert.bind(this);
  }

  componentDidMount() {
    const token = sessionStorage.getItem('token');
    if (token !== null) {
      this.props.authenticationSuccess(token);
    }
  }

  handleClickOpen() {
    this.setState({ open: true });
  }

  handleClose() {
    this.setState({ open: false });
  }

  handleCloseAlert() {
    this.props.closeAlert();
  }

  render() {
    const { auth, user } = this.props;
    return (
      <Router>
        <div className="App">
          <BluredAppBar>
            <Toolbar>
              <Link style={{ color: "inherit" }} to="/">
                <IconButton size="large" edge="start" color="primary" aria-label="home" sx={{ mr: 2 }}>
                  <HomeIcon />
                </IconButton>
              </Link>
              <Breadcrumbs aria-label="breadcrumb">
                <NavLink to="#" sx={{ my: 1, mx: 1.5 }}>
                    공지사항
                </NavLink>
                <NavLink to="/board" sx={{ my: 1, mx: 1.5 }}>
                  게시판
                </NavLink>
              </Breadcrumbs>
              <Box sx={{ flexGrow: 1 }} />
              <SearchBar />
              {auth.isLoggedin ? (
                <div>
                  <IconButton aria-label="user" aria-controls="menu-appbar" aria-haspopup="true" color="inherit">
                    <UserAvatar user={user} />
                  </IconButton>
                </div>
              ) : (
                <Button color="primary" onClick={this.handleClickOpen}>로그인</Button>
              )
              }
            </Toolbar>
          </BluredAppBar>
          <LoginDialog open={this.state.open} onClose={this.handleClose} />
          <Snackbar open={this.props.alert.open} onClose={this.handleCloseAlert} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
            <Alert onClose={this.handleCloseAlert} severity={this.props.alert.type} sx={{ width: `100%` }}>
              {this.props.alert.message}
            </Alert>
          </Snackbar>
        </div>
        <Switch>
          <Route exact path="/" component={auth.isLoggedin ? Home : Landing} />
          <PrivateRoute exact path="/board" component={Board} />
          <PrivateRoute path="/write" component={Write} />
        </Switch>
      </Router>
    );
  }
}

const mapState = (state) => ({
  alert: state.alert,
  auth: state.auth,
  user: state.user
});

const mapDispatch = { authenticationSuccess, openAlert, closeAlert };

export default connect(mapState, mapDispatch)(App);