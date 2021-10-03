import * as React from 'react'
import { Link } from 'react-router-dom'
import { Container, Fab } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';

export default class Board extends React.Component {
  render() {
    return (
      <Container maxWidth={false}>
        <Link to="/write">
          <Fab color="primary" aria-label="add" sx={{ position: 'absolute', bottom: 16, right: 16 }} href="/write">
            <AddIcon />
          </Fab>
        </Link>
        Hello Board!
      </Container>
    )
  }
}