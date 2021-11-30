import * as React from 'react';
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
  FormHelperText,
  FormLabel,
  IconButton } from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import CloseIcon from '@mui/icons-material/Close';
import { connect } from 'react-redux';
import { authenticate, resetError } from '../reducers/AuthReducer';
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

    this.props.resetError();
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

  handleSubmit = (e) => {
    e.preventDefault();
    const data ={
      username: this.state.studentId,
      password: this.state.password
    }
    if (this.validate()) {
      this.props.authenticate(data).unwrap()
        .then((originalPromiseResult) => {
          if (originalPromiseResult.authority === 'ROLE_APPLICANT') {
            const payload = {
              type: alertType.warning,
              message: "등록되지 않은 사용자입니다.\n알고리즘 랩장에게 문의하세요."
            }
            this.props.openAlert(payload);
          }
          this.handleClose();
        })
        .catch((e) => {

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
            {this.props.auth.error ? (
              <FormControl margin="dense" error fullWidth>
                <FormLabel error>
                  {this.props.auth.message}
                </FormLabel>
              </FormControl>
            ) : (
              <FormControl margin="dense" error fullWidth>
              </FormControl>
            )}
            <FormControlLabel control={<Checkbox />} label="자동 로그인" />
            <Button fullWidth variant="contained" size="large" type="submit" sx={{ mt: 1, mb: 2 }}>로그인</Button>
          </Box>
        </DialogContent>
      </Dialog>
    );
  }
}

const mapState = (state) => ({
  auth: state.auth
});

const mapDispatch = { authenticate, resetError, openAlert };

export default connect(mapState, mapDispatch)(LoginDialog);