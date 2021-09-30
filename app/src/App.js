import React from 'react';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { styled, alpha } from '@mui/material/styles';
import { AppBar, Toolbar, Link, IconButton, Breadcrumbs, Box, Button } from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';

import SearchBar from './components/SearchBar';
import LoginDialog from './components/LoginDialog';
import Home from './views/Home';
import Board from './views/Board';
import Write from './views/Write';

const BluredAppBar = styled(AppBar)(({ theme }) => ({
  backdropFilter: 'blur(10px)',
  boxShadow: 'inset 0px -1px 1px #eaeef3',
  backgroundColor: alpha("#ffffff", 0.72)
}));

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state ={
      open: false
    };

    this.handleClickOpen = this.handleClickOpen.bind(this);
    this.handleClose = this.handleClose.bind(this);
  }

  handleClickOpen() {
    this.setState({ open: true });
  }

  handleClose() {
    this.setState({ open: false });
  }

  render() {
    return (
      <Router>
        <div className="App">
          <BluredAppBar>
            <Toolbar>
              <Link style={{ color: "inherit" }} href="/">
                <IconButton size="large" edge="start" color="primary" aria-label="home" sx={{ mr: 2 }}>
                  <HomeIcon />
                </IconButton>
              </Link>
              <Breadcrumbs aria-label="breadcrumb">
                <Link underline="none" href="#" sx={{ my: 1, mx: 1.5 }}>
                  공지사항
                </Link>
                <Link underline="none" href="/board" sx={{ my: 1, mx: 1.5 }}>
                  게시판
                </Link>
              </Breadcrumbs>
              <Box sx={{ flexGrow: 1 }} />
              <SearchBar />
              <Button color="primary" onClick={this.handleClickOpen}>로그인</Button>
            </Toolbar>
          </BluredAppBar>
          <LoginDialog open={this.state.open} onClose={this.handleClose} />
        </div>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/board" component={Board} />
          <Route path="/write" component={Write} />
        </Switch>
      </Router>
    );
  }
}
