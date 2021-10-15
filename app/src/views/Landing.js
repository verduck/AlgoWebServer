import React from 'react';
import { Link } from 'react-router-dom';
import { Container, Grid, Box, Button } from '@mui/material';

class Landing extends React.Component {
  render() {
    return (
      <Container maxWidth={false}>
        <Grid container alignContent="center" style={{ minHeight: "100vh", maxHeight: "100vh" }}>
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