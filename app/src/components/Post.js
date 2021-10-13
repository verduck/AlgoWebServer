import React from 'react';
import { Card, Box, Divider, Typography, Grid, CardContent, CardActions, IconButton } from '@mui/material';
import { styled } from '@mui/material/styles';
import UserAvatar from './UserAvatar';
import FavoriteIcon from '@mui/icons-material/Favorite';
import CommentIcon from '@mui/icons-material/Comment';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

export default class Post extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      post: {
        user: {
          name: '홍길동'
        },
        title: '제목',
        content: '내용',
        createdAt: Date.now(),
        updatedAt: null
      },
      expanded: false
    }
  }
  render() {
    return (
      <Card sx={{ m: 2 }}>
        <Box sx={{ p: 2, display: 'flex' }}>
          <UserAvatar user={this.state.post.user} />
          <Grid sx={{ mx: 2, width: '100%' }}>
            <Typography variant="h5">{this.state.post.title}</Typography>
            <Grid container direction="row" justifyContent="flex-start" alignItems="center">
              <Typography variant="subtitle1">{this.state.post.user.name}</Typography>
              <Typography variant="caption" sx={{ mx: 1, color: 'text.secondary' }}>{this.state.post.createdAt.toString()}</Typography>
            </Grid>
          </Grid>
        </Box>
        <Divider />
        <CardContent>
          {this.state.post.content}
        </CardContent>
        <CardActions disableSpacing>
          <IconButton aria-label="add to favorites">
            <FavoriteIcon />
          </IconButton>
          <IconButton aria-label="show comment">
            <CommentIcon />
          </IconButton>
          <IconButton aria-label="share">
            <ShareIcon />
          </IconButton>
          <ExpandMore expand={this.state.expanded} aria-expanded={this.state.expanded} aria-label="show more">
            <ExpandMoreIcon />
          </ExpandMore>
        </CardActions>
      </Card>
    )
  }
}