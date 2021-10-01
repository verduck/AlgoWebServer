import React from 'react'
import { Container, Grid, Box } from '@mui/material';

class Home extends React.Component {
  render() {
    return (
      <Container maxWidth={false}>
        <Grid container alignContent="center" style={{ minHeight: "100vh", maxHeight: "100vh" }}>
          <Box textAlign="center" mx="auto">
            흥보 페이지
          </Box>
        </Grid>
      </Container>
    )
  }
}

export default Home;