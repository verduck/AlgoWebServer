import * as React from 'react'
import { Container, Grid, Box } from '@mui/material';

class Home extends React.Component {
  render() {
    return (
      <Container maxWidth={false} style={{ height: "100vh" }}>
        <Grid container alignContent="center" height="100%">
          <Box sx={{ height: '64px' }} />
          <Box textAlign="center" mx="auto">
            Hello World
          </Box>
        </Grid>
      </Container>
    )
  }
}

export default Home;