import React from 'react';
import { Container, Grid, Box, Typography, Divider, FormControl, OutlinedInput, ButtonGroup, IconButton } from '@mui/material';
import { Editor, EditorState} from "draft-js";
import { styled } from '@mui/material/styles';

const EditorControl = styled(FormControl)(({ theme }) => ({
	border: 'solid grey',
	borderWidth: 'thin',
  borderRadius: theme.shape.borderRadius,
	'&:hover': {
		borderColor: theme.palette.text.primary
	},
}));

class Write extends React.Component {
	constructor(props) {
		super(props);
		this.state = { editorState: EditorState.createEmpty() };
	}

	setEditor = (editor) => {
		this.editor = editor;
	}

	onEditorStateChange = (editorState) => {
		this.setState({ editorState });
	}

	onFocusEditor = () => {
		if (this.editor) {
			this.editor.focus();
		}
	}

	render() {
		return(
			<Container maxWidth={false}>
				<Grid container alignContent="center" style={{ minHeight: "100vh" }}>
					<Box textAlign="center" mx="auto">
						<Typography align="left" gutterBottom  variant="h4">게시물 작성</Typography>
						<Divider />
						<Box component="form" width="512dp">
							<FormControl margin="normal" sx={{ width: '100ch' }} onClick={this.forceUpdate}>
								<OutlinedInput placeholder="제목" />
							</FormControl>
							<EditorControl margin="normal" sx={{ width: '100ch' }}>
								<Editor ref={this.setEditor} editorState={this.state.editorState} onChange={this.onEditorStateChange} />
							</EditorControl>
						</Box>
					</Box>
				</Grid>
			</Container>
		)
	}
}

export default Write;