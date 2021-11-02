import React from 'react';
import { Link } from 'react-router-dom';
import { Container, Grid, Box, Button, Toolbar } from '@mui/material';

class Landing extends React.Component {
  render() {
    return (
      <Container maxWidth={false}>
        <Toolbar />
        <Grid container alignContent="center" style={{ minHeight: "95vh", maxHeight: "95vh" }}>
          <Box textAlign="center" mx="auto">
            흥보 페이지
          </Box>
          <Box textAlign="center" mx="auto">
            <Link to="/application">
              <Button>지원하기</Button>
            </Link>
          </Box>
        </Grid>
        
      </Container>
    )
  }
}

export default Landing;