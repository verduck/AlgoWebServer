import * as React from 'react'
import { Container, Grid, Box, Typography, Divider, Stepper } from '@mui/material';

class Application extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      step: 0
    }

    this.isAuthStep = this.isAuthStep.bind(this);
  }

  isAuthStep() {
    return this.state.step === 0;
  }

  render() {
    return (
      <Container maxWidth={false} style={{ height: "100vh" }}>
        <Box sx={{ height: '64px' }} />
				<Grid container alignContent="center" height="91%">
					<Box mx="auto" width="1920px" maxWidth="1920px">
            <Typography align="left" gutterBottom  variant="h4">지원서 작성</Typography>
            <Divider />
            <Box textAlign="center" alignContent="center" mx="auto" height="800px">
            <Stepper activeStep={0} alternativeLabel />
            {this.isAuthStep() ? (
              <div>
                인증
              </div>
              
            ) : (
              <div>g</div>
            )}
            </Box>
					</Box>
				</Grid>
			</Container>
		);
  }
}

export default Application;