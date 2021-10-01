import * as React from 'react';
import axios from 'axios';
import {
  Dialog,
  DialogContent,
  Box,
  TextField,
  FormControl,
  InputLabel,
  OutlinedInput,
  DialogTitle,
  InputAdornment,
  Button,
  Checkbox,
  FormControlLabel,
  Snackbar,
  Alert,
  FormHelperText,
  FormLabel,
  IconButton } from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import CloseIcon from '@mui/icons-material/Close';
import { connect } from 'react-redux';
import { authenticationSuccess } from '../reducers/AuthReducer';
import { alertType, openAlert } from '../reducers/AlertReducer';

class LoginDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      studentId: '',
      password: '',
      showPassword: false,
      studentIdError: false,
      studentIdErrorMessage: '',
      passwordError: false,
      passwordErrorMessage: '',
      snackbar: {
        open: false,
        message: '',
      },
      login: {
        error: false,
        message: '',
      }
    }
  }

  handleOpenSnackbar = (m) => {
    this.setState({snackbar:{open: true, message: m}})
  }

  handleCloseSnackbar = () => {
    this.setState({snackbar:{open: false, message: ''}})
  }

  handleChange = (event) => {
    this.setState({[event.target.name]: event.target.value})
    if (event.target.name === 'studentId' && event.target.value !== '') {
      this.setState({ studentIdError: false, studentIdErrorMessage: '' });
    }
    if (event.target.name === 'password' && event.target.value !== '') {
      this.setState({ passwordError: false, passwordErrorMessage: '' });
    }
  }

  handleClose = () => {
    this.setState({
      studentId: '',
      password: '',
      showPassword: false,
      studentIdError: false,
      studentIdErrorMessage: '',
      passwordError: false,
      passwordErrorMessage: '',
      snackbar: {
        open: false,
        message: '',
      },
      login: {
        error: false,
        message: '',
      }
    });
    this.props.onClose();
  }

  handleClickShowPassword = () => {
    this.setState({showPassword: !this.state.showPassword});
  }

  validate = () => {
    const valid = {
      studentIdError: false,
      studentIdErrorMessage: '',
      passwordError: false,
      passwordErrorMessage: '',
    };

    if (this.state.studentId === '') {
      valid.studentIdError = true;
      valid.studentIdErrorMessage = '학번을 입력하세요.';
    } else {
      valid.studentIdError = false;
      valid.studentIdErrorMessage = '';
    }

    if (this.state.password === '') {
      valid.passwordError = true;
      valid.passwordErrorMessage = '비밀번호를 입력하세요.';
    } else {
      valid.passwordError = false;
      valid.passwordErrorMessage = '';
    }

    this.setState({
      studentIdError: valid.studentIdError,
      studentIdErrorMessage: valid.studentIdErrorMessage,
      passwordError: valid.passwordError,
      passwordErrorMessage: valid.passwordErrorMessage
    });
    return !valid.studentIdError && !valid.passwordError;
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const data ={
      username: this.state.studentId,
      password: this.state.password
    }
    if (this.validate()) {
      axios.post('/api/v1/auth/authenticate', data)
        .then(res => {
          this.props.authenticationSuccess(res.data.token);
          this.handleClose();
        })
        .catch(err => {
          if (err.response) {
            const data = err.response.data;
            this.setState({login: {error: !data.result, message: data.message}});
            if (data.result) {
              const payload = {
                type: alertType.warning,
                message: data.message
              };
              this.props.openAlert(payload);
            }
          }
        });
    }
  }

  render() {
    return (
      <Dialog open={this.props.open} onClose={this.handleClose}>
        <DialogTitle>
          로그인
          {this.props.onClose ? (
            <IconButton arial-label="close" onClick={this.handleClose}
              sx={{
                position: 'absolute',
                right: 8,
                top: 8,
                color: (theme) => theme.palette.grey[500]
              }}
            >
              <CloseIcon />
            </IconButton>
          ) : null}
        </DialogTitle>
        <DialogContent>
          <Box component="form" noValidate sx={{ width: '100%' }} onSubmit={this.handleSubmit}>
            <TextField margin="normal" error={this.state.studentIdError} helperText={this.state.studentIdErrorMessage} id="student-id" name="studentId" label="학번" value={this.state.studentId} onChange={this.handleChange} type="text" fullWidth />
            <FormControl margin="normal" error={this.state.passwordError} fullWidth variant="outlined">
              <InputLabel htmlFor="outlined-adornment-password">비밀번호</InputLabel>
              <OutlinedInput margin="dense" id="password" name="password" label="비밀번호" value={this.state.password} onChange={this.handleChange} type={this.state.showPassword ? 'text' : 'password'} fullWidth
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton aria-label="toggle password visibility" onClick={this.handleClickShowPassword} edge="end">
                      {this.state.showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                } />
                <FormHelperText error={this.state.passwordError}>
                  {this.state.passwordErrorMessage}
                </FormHelperText>
            </FormControl>
            {this.state.login.error ? (
              <FormControl margin="dense" error fullWidth>
                <FormLabel error>
                  {this.state.login.message}
                </FormLabel>
              </FormControl>
            ) : null}
            <FormControlLabel control={<Checkbox />} label="자동 로그인" />
            <Button fullWidth variant="contained" size="large" type="submit" sx={{ mt: 1, mb: 2 }}>로그인</Button>
          </Box>
        </DialogContent>
        <Snackbar open={this.state.snackbar.open} onClose={this.handleCloseSnackbar} anchorOrigin={{ vertical: 'top', horizontal: 'center' }}>
          <Alert onClose={this.handleCloseSnackbar} severity="warning" sx={{ width: `100%` }}>
            {this.state.snackbar.message}
          </Alert>
        </Snackbar>
      </Dialog>
    );
  }
}

const mapState = (state) => ({
  auth: state.auth
});

const mapDispatch = { authenticationSuccess, openAlert };

export default connect(mapState, mapDispatch)(LoginDialog);