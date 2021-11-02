import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { styled, alpha } from '@mui/material/styles';
import { Toolbar, IconButton, Box, Stack, Button, Snackbar, Alert, Divider, Menu, List, ListItem, ListItemIcon, ListItemText, Icon } from '@mui/material';
import MuiAppBar from '@mui/material/AppBar';
import MuiDrawer from '@mui/material/Drawer';
import HomeIcon from '@mui/icons-material/Home';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';

import CampaignIcon from '@mui/icons-material/Campaign';
import AssignmentIcon from '@mui/icons-material/Assignment';

import PrivateRoute from './components/PrivateRoute';
import SearchBar from './components/SearchBar';
import LoginDialog from './components/LoginDialog';
import UserAvatar from './components/UserAvatar';

import Home from './views/Home';
import Landing from './views/Landing';
import Application from './views/Application';
import Board from './views/Board';
import Write from './views/Write';

import { connect } from 'react-redux';
import { authenticationSuccess } from './reducers/AuthReducer';
import { openAlert, closeAlert } from './reducers/AlertReducer';

const RouterLink = require('react-router-dom').Link;

const drawerWidth = 240;

const openedMixin = (theme) => ({
  width: drawerWidth,
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: 'hidden',
});

const closedMixin = (theme) => ({
  transition: theme.transitions.create('width', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: 'hidden',
  width: `calc(${theme.spacing(7)} + 1px)`,
});

const DrawerHeader = styled('div')(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-end',
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
  backdropFilter: 'blur(10px)',
  boxShadow: 'inset 0px -1px 1px #eaeef3',
  backgroundColor: alpha("#ffffff", 0.72),
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(['width', 'margin'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
  ({ theme, open }) => ({
    width: drawerWidth,
    flexShrink: 0,
    whiteSpace: 'nowrap',
    boxSizing: 'border-box',
    ...(open && {
      ...openedMixin(theme),
      '& .MuiDrawer-paper': openedMixin(theme),
    }),
    ...(!open && {
      ...closedMixin(theme),
      '& .MuiDrawer-paper': closedMixin(theme),
    }),
  }),
);

const Link = styled(RouterLink)(({ theme }) => ({
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
      openDrawer: false,
      openLogin: false
    };

    this.handleOpenLoginDialog = this.handleOpenLoginDialog.bind(this);
    this.handleCloseLoginDialog = this.handleCloseLoginDialog.bind(this);
    this.handleCloseAlert = this.handleCloseAlert.bind(this);
    this.handleOpenDrawer = this.handleOpenDrawer.bind(this);
    this.handleCloseDrawer = this.handleCloseDrawer.bind(this);
  }

  componentDidMount() {
    const token = sessionStorage.getItem('token');
    if (token !== null) {
      this.props.authenticationSuccess(token);
    }
  }

  handleOpenLoginDialog() {
    this.setState({ openLogin: true });
  }

  handleCloseLoginDialog() {
    this.setState({ openLogin: false });
  }

  handleCloseAlert() {
    this.props.closeAlert();
  }

  handleOpenDrawer() {
    this.setState({ openDrawer: true });
  }

  handleCloseDrawer() {
    this.setState({ openDrawer: false });
  }

  render() {
    const { auth, user } = this.props;
    const jjLinkItems = [
      { label: '전주대학교', url: 'https://jj.ac.kr' },
      { label: '컴퓨터공학과', url: 'https://jj.ac.kr/cse' },
      { label: '사이버캠퍼스', url: 'https://cyber.jj.ac.kr' },
      { label: 'inSTAR', url: 'https://instar.jj.ac.kr' },
      { label: '학생역량개발시스템', url: 'https://onstar.jj.ac.kr' }
    ];
    const menuItems = [
      { label: '공지사항', path: '/notice', icon: <CampaignIcon /> },
      { label: '게시판', path: '/board', icon: <AssignmentIcon /> },
    ];
    return (
      <Router>
        <Box className="App" sx={{ display: "flex" }}>
          <AppBar open={this.state.openDrawer}>
            <Toolbar>
              <IconButton
                color="primary"
                aria-label="open drawer"
                onClick={this.handleOpenDrawer}
                edge="start"
                sx={{
                  marginRight: '36px',
                  ...(this.state.openDrawer && { display: 'none' }),
                }}
              >
                <MenuIcon />
              </IconButton>
              <Link style={{ color: "inherit" }} to="/">
                <IconButton size="large" edge="start" color="primary" aria-label="home" sx={{ mr: 2 }}>
                  <HomeIcon />
                </IconButton>
              </Link>
              <Box sx={{ flexGrow: 1 }} />
              <SearchBar />
              {auth.isLoggedin ? (
                <div>
                  <IconButton aria-label="user" aria-controls="menu-appbar" aria-haspopup="true" color="inherit">
                    <UserAvatar user={user} />
                  </IconButton>
                </div>
              ) : (
                <Button color="primary" onClick={this.handleOpenLoginDialog}>로그인</Button>
              )
              }
            </Toolbar>
          </AppBar>
          <Drawer variant="permanent" open={this.state.openDrawer}>
            <DrawerHeader>
              <IconButton onClick={this.handleCloseDrawer}>
                <ChevronLeftIcon />
              </IconButton>
            </DrawerHeader>
            {this.state.openDrawer ? (
              <Divider />
            ) : (
              <Divider variant="inset" />
            )}
            <List>
              {menuItems.map(({ label, path, icon }, index) => (
                <Link style={{ color: "inherit" }} to={path} key={index}>
                  <ListItem button>
                    <ListItemIcon>
                      {icon}
                    </ListItemIcon>
                    <ListItemText primary={label} />
                  </ListItem>
                </Link>
              ))}
            </List>
            {this.state.openDrawer ? (
              <div>
                <Divider />
                <Stack spacing={2} sx={{ m: 2}}>
                  {jjLinkItems.map(({ label, url }, index) => (
                    <Button variant="outlined" key={index} href={url} target="_blank">
                      {label}
                    </Button>
                  ))}
                </Stack>
              </div>
            ) : (
              null
            )}
          </Drawer>
          <LoginDialog open={this.state.openLogin} onClose={this.handleCloseLoginDialog} />
          <Snackbar open={this.props.alert.open} onClose={this.handleCloseAlert} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
            <Alert onClose={this.handleCloseAlert} severity={this.props.alert.type} sx={{ width: `100%` }}>
              {this.props.alert.message}
            </Alert>
          </Snackbar>
          <Switch>
            <Route exact path="/" component={auth.isLoggedin ? Home : Landing} />
            <Route exact path="/application" component={Application} />
            <PrivateRoute exact path="/board" component={Board} />
            <PrivateRoute path="/write" component={Write} />
          </Switch>
        </Box>
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