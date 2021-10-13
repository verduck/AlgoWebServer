import * as React from 'react'
import { Link } from 'react-router-dom'
import { Container, Box, Fab, List } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import Post from '../components/Post';

export default class Board extends React.Component {
  render() {
    return (
      <Container maxWidth={false}>
        <Box sx={{ height: '64px' }} />
        <List>
          <Post />
          <Post />
        </List>
        <Link to="/write">
          <Fab color="primary" aria-label="add" sx={{ position: 'fixed', display: '', bottom: 16, right: 16 }}>
            <AddIcon />
          </Fab>
        </Link>
      </Container>
    );
  }
}