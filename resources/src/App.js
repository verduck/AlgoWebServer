import * as React from 'react';
import { styled, alpha } from '@mui/material/styles';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Link from '@mui/material/Link';
import IconButton from '@mui/material/IconButton';
import HomeIcon from '@mui/icons-material/Home';
import SearchBar from './components/SearchBar';
import Button from '@mui/material/Button';
import LoginDialog from './components/LoginDialog';
import Home from './views/Home';
import Board from './views/Board';
import { Typography } from '@mui/material';
import Breadcrumbs from '@mui/material/Breadcrumbs'

const BluredAppBar = styled(AppBar)(({ theme }) => ({
  backdropFilter: 'blur(20px)',
  boxShadow: 'inset 0px -1px 1px #eaeef3',
  backgroundColor: alpha("#ffffff", 0.72)
}));

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false
    };
  }

  handleClickOpen = () => {
    this.setState({open: true});
  };

  handleClose = () => {
    this.setState({open: false});
  };

  render() {
    return (
      <Router>
        <div className="App">
          <BluredAppBar position="sticky">
            <Toolbar>
              <Link style={{ color: "inherit" }} href="/">
                <IconButton
                  size="large"
                  edge="start"
                  color="primary"
                  aria-label="menu"
                  sx={{ mr: 2 }}
                >
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
              {true ? (
                <Button color="primary" onClick={this.handleClickOpen}>로그인</Button>
                ) : (
                <Typography component="div">
                  이름
                </Typography>
              )}
            </Toolbar>
          </BluredAppBar>
          <LoginDialog open={this.state.open} onClose={this.handleClose} />
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/board" component={Board} />
          </Switch>
        </div>
      </Router>
    );
  }
}
