import * as React from 'react'
import {
  Container,
  Grid,
  Box,
  Typography,
  Divider,
  Stepper,
  TextField,
  FormControl,
  InputLabel,
  OutlinedInput,
  InputAdornment,
  IconButton,
  FormHelperText,
  FormLabel,
  Button
} from '@mui/material';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Visibility from '@mui/icons-material/Visibility';

class Application extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      step: 0,
      studentId: '',
      password: '',
      showPassword: false,
      studentIdError: false,
      studentIdErrorMessage: '',
      passwordError: false,
      passwordErrorMessage: '',
      login: {
        error: false,
        message: ''
      }
    }

    this.isAuthStep = this.isAuthStep.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleClickShowPassword = this.handleClickShowPassword.bind(this);
    this.handleAuth = this.handleAuth.bind(this);
  }

  isAuthStep() {
    return this.state.step === 0;
  }

  handleChange(e) {
    this.setState({[e.target.name]: e.target.value})
    if (e.target.name === 'studentId' && e.target.value !== '') {
      this.setState({ studentIdError: false, studentIdErrorMessage: '' });
    }
    if (e.target.name === 'password' && e.target.value !== '') {
      this.setState({ passwordError: false, passwordErrorMessage: '' });
    }
  }

  handleClickShowPassword() {
    this.setState({showPassword: !this.state.showPassword});
  }

  handleAuth(e) {
    e.preventDefault();
    this.setState({ step: 1 });
  }

  render() {
    return (
      <Container maxWidth={false} style={{ height: "100vh" }}>
        <Box sx={{ height: '64px' }} />
				<Grid container alignContent="center" height="91%">
					<Box mx="auto" width="1920px" maxWidth="1920px">
            <Typography align="left" gutterBottom  variant="h4">지원서 작성</Typography>
            <Divider />
            <Box textAlign="center" alignContent="center" height="800px">
              <Stepper activeStep={0} alternativeLabel />
              {this.isAuthStep() ? (
                <Box component="form" noValidate sx={{ width: '100%' }} onSubmit={this.handleAuth}>
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
                  <Button fullWidth variant="contained" size="large" type="submit" sx={{ mt: 1, mb: 2 }}>로그인</Button>
                </Box>
              ) : (
                <Box component="form" noValidate sx={{ width: '100%' }} onSubmit={this.handleSubmit}>
                </Box>
              )}
            </Box>
					</Box>
				</Grid>
			</Container>
		);
  }
}

export default Application;